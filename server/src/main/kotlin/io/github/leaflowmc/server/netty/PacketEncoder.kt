package io.github.leaflowmc.server.netty

import io.github.leaflowmc.common.utils.writeVarInt
import io.github.leaflowmc.protocol.packets.ClientPacket
import io.github.leaflowmc.serialization.minecraft_format.encodePacket
import io.netty.buffer.ByteBuf
import io.netty.channel.ChannelHandlerContext
import io.netty.handler.codec.MessageToByteEncoder
import kotlinx.serialization.KSerializer
import org.apache.logging.log4j.LogManager

class PacketEncoder: MessageToByteEncoder<ClientPacket<*, *>>() {
    override fun encode(ctx: ChannelHandlerContext, msg: ClientPacket<*, *>, out: ByteBuf) {
        out.writeVarInt(msg.getType().id)
        out.encodePacket(
            msg.getType().serializer as KSerializer<ClientPacket<*, *>>,
            msg
        )
    }
}