package io.github.leaflowmc.leaflow.server.netty

import io.github.leaflowmc.leaflow.protocol.ProtocolStage
import io.github.leaflowmc.leaflow.protocol.listener.server.ServerPacketListener
import io.github.leaflowmc.leaflow.protocol.packets.ClientPacket
import io.github.leaflowmc.leaflow.protocol.packets.Packet
import io.github.leaflowmc.leaflow.server.LeaflowServer
import io.github.leaflowmc.leaflow.server.player.PlayerConnection
import io.netty.channel.Channel
import io.netty.channel.ChannelHandlerContext
import io.netty.channel.ChannelInboundHandlerAdapter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import org.apache.logging.log4j.LogManager

class PlayerConnectionImpl(
    override val server: LeaflowServer,
    initial: ProtocolStage
) : ChannelInboundHandlerAdapter(), PlayerConnection {
    companion object {
        private val LOGGER = LogManager.getLogger()
    }

    override var protocol: ProtocolStage = initial
        set(value) {
            packetListener = server.factory.createServerPacketListenerFor(value, this)

            val future = channel.writeAndFlush(ChannelInboundProtocolSwapper.Task { ctx ->
                channel.pipeline().replace(ctx.name(), "decoder", PacketDecoder(value))
                channel.config().isAutoRead = true
            })

            try {
                future.syncUninterruptibly()
            } catch (e: Throwable) {
                LOGGER.error("Exception during protocol change", e)
                throw e
            }
            field = value
        }

    private var packetListener = server.factory.createServerPacketListenerFor(protocol, this)

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

}