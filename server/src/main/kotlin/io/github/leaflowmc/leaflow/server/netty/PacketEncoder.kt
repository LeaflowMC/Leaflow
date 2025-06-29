package io.github.leaflowmc.leaflow.server.netty

import io.github.leaflowmc.leaflow.common.utils.writeVarInt
import io.github.leaflowmc.leaflow.protocol.packets.Packet
import io.github.leaflowmc.leaflow.protocol.packets.type.ProtocolInfo
import io.github.leaflowmc.leaflow.serialization.minecraft_format.encode
import io.github.leaflowmc.leaflow.server.constants.NettyHandlerConstants.PACKET_ENCODER_SWAPPER
import io.netty.buffer.ByteBuf
import io.netty.channel.ChannelHandlerContext
import io.netty.handler.codec.MessageToByteEncoder
import kotlinx.serialization.SerializationStrategy
import org.apache.logging.log4j.LogManager

class PacketEncoder(
    private val protocolInfo: ProtocolInfo
): MessageToByteEncoder<Packet<*>>() {
    companion object {
        private val LOGGER = LogManager.getLogger()
    }

    @Suppress("UNCHECKED_CAST")
    override fun encode(ctx: ChannelHandlerContext, msg: Packet<*>, out: ByteBuf) {
        val id = protocolInfo[msg::class]

        if (id == -1) {
            LOGGER.error("can't encode packet with protocol ${protocolInfo.protocolStage}: '${msg::class.simpleName}'")
            return
        }

        val seri = checkNotNull(protocolInfo[id]) {
            "got a valid id $id but serializer was null in protocol ${protocolInfo.protocolStage} (${msg::class.simpleName})"
        }

        out.writeVarInt(id)
        out.encode(
            seri as SerializationStrategy<Packet<*>>,
            msg
        )

        if (msg.terminal) {
            ctx.channel().pipeline()
                .addBefore(ctx.name(), PACKET_ENCODER_SWAPPER, ChannelOutboundProtocolSwapper())
                .remove(ctx.name())
        }
    }
}
