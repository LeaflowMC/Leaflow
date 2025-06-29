package io.github.leaflowmc.leaflow.server.netty

import io.github.leaflowmc.leaflow.common.api.Disposable
import io.github.leaflowmc.leaflow.common.api.Tickable
import io.github.leaflowmc.leaflow.common.utils.ticks
import io.github.leaflowmc.leaflow.protocol.ProtocolStage
import io.github.leaflowmc.leaflow.protocol.listener.server.ServerPacketListener
import io.github.leaflowmc.leaflow.protocol.packets.ClientPacket
import io.github.leaflowmc.leaflow.protocol.packets.Packet
import io.github.leaflowmc.leaflow.protocol.packets.common.ClientboundDisconnectPacket
import io.github.leaflowmc.leaflow.protocol.packets.login.ClientboundLoginDisconnectPacket
import io.github.leaflowmc.leaflow.protocol.packets.type.getClientProtocolFor
import io.github.leaflowmc.leaflow.protocol.packets.type.getServerProtocolFor
import io.github.leaflowmc.leaflow.server.LeaflowServer
import io.github.leaflowmc.leaflow.server.constants.EncryptionConstants.ENCRYPTION_CIPHER
import io.github.leaflowmc.leaflow.server.constants.NettyHandlerConstants.CIPHER_DECODER
import io.github.leaflowmc.leaflow.server.constants.NettyHandlerConstants.CIPHER_ENCODER
import io.github.leaflowmc.leaflow.server.constants.NettyHandlerConstants.LENGTH_DECODER
import io.github.leaflowmc.leaflow.server.constants.NettyHandlerConstants.LENGTH_ENCODER
import io.github.leaflowmc.leaflow.server.constants.NettyHandlerConstants.PACKET_DECODER
import io.github.leaflowmc.leaflow.server.constants.NettyHandlerConstants.PACKET_ENCODER
import io.github.leaflowmc.leaflow.server.constants.TextConstants
import io.github.leaflowmc.leaflow.server.constants.TextConstants.DISCONNECT_INVALID_PACKET
import io.github.leaflowmc.leaflow.server.encryption.PacketDecryptor
import io.github.leaflowmc.leaflow.server.encryption.PacketEncryptor
import io.github.leaflowmc.leaflow.server.packets.api.LeaflowServerCommonPacketListener
import io.github.leaflowmc.leaflow.server.player.PlayerConnection
import io.github.leaflowmc.leaflow.text.component.PlainTextComponent
import io.github.leaflowmc.leaflow.text.component.TextComponent
import io.github.leaflowmc.leaflow.text.component.TranslatedTextComponent
import io.netty.channel.Channel
import io.netty.channel.ChannelHandlerContext
import io.netty.channel.ChannelInboundHandlerAdapter
import kotlinx.coroutines.*
import org.apache.logging.log4j.LogManager
import java.security.Key
import javax.crypto.Cipher
import javax.crypto.spec.IvParameterSpec
import kotlin.time.Duration

class PlayerConnectionImpl(
    override val server: LeaflowServer,
    initial: ProtocolStage
) : ChannelInboundHandlerAdapter(), PlayerConnection {
    companion object {
        private val LOGGER = LogManager.getLogger()
    }

    override var protocol: ProtocolStage = initial
        private set
    private var packetListener = server.factory.createServerPacketListenerFor(protocol, this)

    override var encryptionEnabled: Boolean = false
        private set

    private lateinit var channel: Channel
    private val scope = CoroutineScope(Dispatchers.IO)

    init {
        scope.launch {
            while (true) {
                delay(1.ticks)
                tick()
            }
        }
    }

    private fun tick() {
        (packetListener as? Tickable)?.tick()
    }

    override fun disconnect(reason: TextComponent) {
        val packet = when (protocol) {
            ProtocolStage.LOGIN -> ClientboundLoginDisconnectPacket(reason)

            ProtocolStage.PLAY,
            ProtocolStage.CONFIGURATION -> ClientboundDisconnectPacket(reason)

            else -> null
        }

        packet?.let(::sendPacket)

        channel.close()
    }

    override fun sendPing(): Deferred<Duration>? {
        val listener = packetListener

        if (listener is LeaflowServerCommonPacketListener) {
            return listener.sendPing()
        }
        return null
    }

    override fun setEncryptionKey(key: Key) {
        encryptionEnabled = true

        val decipher = Cipher.getInstance(ENCRYPTION_CIPHER)
        decipher.init(Cipher.DECRYPT_MODE, key, IvParameterSpec(key.encoded))

        val encipher = Cipher.getInstance(ENCRYPTION_CIPHER)
        encipher.init(Cipher.ENCRYPT_MODE, key, IvParameterSpec(key.encoded))

        channel.pipeline()
            .addBefore(LENGTH_ENCODER, CIPHER_ENCODER, PacketEncryptor(encipher))
            .addBefore(LENGTH_DECODER, CIPHER_DECODER, PacketDecryptor(decipher))
    }

    override fun setOutboundProtocol(stage: ProtocolStage) {
        val future = channel.writeAndFlush(ChannelOutboundProtocolSwapper.Task { ctx ->
            channel.pipeline().replace(ctx.name(), PACKET_ENCODER, PacketEncoder(getClientProtocolFor(stage)))
        })

        try {
            future.syncUninterruptibly()
        } catch (e: Throwable) {
            LOGGER.error("Exception during protocol change", e)
            throw e
        }

    }

    override fun setInboundProtocol(stage: ProtocolStage, listener: ServerPacketListener) {
        val future = channel.writeAndFlush(ChannelInboundProtocolSwapper.Task { ctx ->
            channel.pipeline().replace(ctx.name(), PACKET_DECODER, PacketDecoder(getServerProtocolFor(stage)))
            channel.config().isAutoRead = true
        })

        try {
            future.syncUninterruptibly()
        } catch (e: Throwable) {
            LOGGER.error("Exception during protocol change", e)
            throw e
        }

        (packetListener as? Disposable)?.dispose()
        packetListener = listener
    }

    override fun channelActive(ctx: ChannelHandlerContext) {
        super.channelActive(ctx)
        channel = ctx.channel()
    }

    @Suppress("UNCHECKED_CAST")
    override fun channelRead(ctx: ChannelHandlerContext, msg: Any) {
        try {
            (msg as Packet<ServerPacketListener>).handle(packetListener)
        } catch (e: ClassCastException) {
            LOGGER.error(
                "The listener \"${packetListener::class.simpleName}\" can't handle the packet \"${msg::class.simpleName}\"",
                e
            )
            disconnect(TranslatedTextComponent(DISCONNECT_INVALID_PACKET))
        }
    }

    override fun channelInactive(ctx: ChannelHandlerContext) {
        scope.cancel("Channel is inactive")
        (packetListener as? Disposable)?.dispose()

        super.channelInactive(ctx)
    }

    override fun exceptionCaught(ctx: ChannelHandlerContext, cause: Throwable) {
        LOGGER.error("Caught exception", cause)

        disconnect(PlainTextComponent("Internal exception: ${cause.message}"))
    }

    override fun sendPacket(packet: ClientPacket<*>) {
        channel.writeAndFlush(packet)
    }
}
