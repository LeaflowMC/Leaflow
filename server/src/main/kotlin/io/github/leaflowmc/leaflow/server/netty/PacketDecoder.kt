package io.github.leaflowmc.leaflow.server.netty

import io.github.leaflowmc.leaflow.common.utils.readVarInt
import io.github.leaflowmc.leaflow.protocol.packets.type.ProtocolInfo
import io.github.leaflowmc.leaflow.serialization.minecraft_format.decode
import io.github.leaflowmc.leaflow.server.constants.NettyHandlerConstants.PACKET_DECODER_SWAPPER
import io.netty.buffer.ByteBuf
import io.netty.channel.ChannelHandlerContext
import io.netty.handler.codec.ByteToMessageDecoder
import org.apache.logging.log4j.LogManager

class PacketDecoder(
    private val protocolInfo: ProtocolInfo
) : ByteToMessageDecoder() {
    companion object {
        private val LOGGER = LogManager.getLogger()
    }

    override fun decode(
        ctx: ChannelHandlerContext,
        buffer: ByteBuf,
        out: MutableList<Any>
    ) {
        val protocolId = buffer.readVarInt()

        val seri = protocolInfo[protocolId]

        if (seri == null) {
            LOGGER.error("received unknown packet with id $protocolId: ${protocolInfo.getSkippedName(protocolId)}")
            buffer.skipBytes(buffer.readableBytes())
            return
        }

        val packet = buffer.decode(seri)
        out.add(packet)

        // prevent more packets from reading while we are switching protocols
        if (packet.terminal) {
            ctx.channel().config().isAutoRead = false
            ctx.channel().pipeline()
                .addBefore(ctx.name(), PACKET_DECODER_SWAPPER, ChannelInboundProtocolSwapper())
                .remove(ctx.name())
        }
    }
}