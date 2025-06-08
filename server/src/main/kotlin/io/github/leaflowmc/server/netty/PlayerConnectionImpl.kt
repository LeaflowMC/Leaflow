package io.github.leaflowmc.server.netty

import io.github.leaflowmc.protocol.ProtocolStage
import io.github.leaflowmc.protocol.listener.server.ServerPacketListener
import io.github.leaflowmc.protocol.packets.ClientPacket
import io.github.leaflowmc.protocol.packets.Packet
import io.github.leaflowmc.server.LeaflowServer
import io.github.leaflowmc.server.player.PlayerConnection
import io.netty.channel.Channel
import io.netty.channel.ChannelHandlerContext
import io.netty.channel.ChannelInboundHandlerAdapter
import kotlinx.coroutines.*
import org.apache.logging.log4j.LogManager
import java.nio.channels.ClosedChannelException

class PlayerConnectionImpl(
    override val server: LeaflowServer,
    override val protocol: ProtocolStage
) : ChannelInboundHandlerAdapter(), PlayerConnection {
    companion object {
        private val LOGGER = LogManager.getLogger()
    }

    var packetListener = server.factory.createServerPacketListenerFor(protocol, this)

    private val scope = CoroutineScope(Dispatchers.Default + SupervisorJob())
    private lateinit var channel: Channel

    override fun channelActive(ctx: ChannelHandlerContext) {
        super.channelActive(ctx)
        channel = ctx.channel()
    }

    @Suppress("UNCHECKED_CAST")
    override fun channelRead(ctx: ChannelHandlerContext, msg: Any) {
            try {
                (msg as Packet<ServerPacketListener, *>).handle(packetListener)
            } catch (e: ClassCastException) {
                LOGGER.error(
                    "The listener \"${packetListener::class.simpleName}\" can't handle the packet \"${msg::class.simpleName}\"",
                    e
                )
                // TODO disconnect packet
                ctx.close()
            } catch (e: Throwable) {
                LOGGER.error("error while handling packet \"${msg::class.simpleName}\"", e)
                throw e
            }
    }

    override fun sendPacket(packet: ClientPacket<*, *>) {
        channel.writeAndFlush(packet)
    }

    override fun changeProtocol(protocol: ProtocolStage) {
        packetListener = server.factory.createServerPacketListenerFor(protocol, this)

        val future = channel.writeAndFlush(ChannelInboundProtocolSwapper.Task { ctx ->
            channel.pipeline().replace(ctx.name(), "decoder", PacketDecoder(protocol))
            channel.config().isAutoRead = true
            LOGGER.info("changed back to connection in $protocol")
        })

        try {
            future.syncUninterruptibly()
        } catch (_: ClosedChannelException) {
            LOGGER.info("Closed connection during protocol change")
        }
    }
}