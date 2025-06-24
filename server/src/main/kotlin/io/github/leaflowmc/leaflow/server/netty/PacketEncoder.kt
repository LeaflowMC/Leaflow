package io.github.leaflowmc.leaflow.server.netty

import io.github.leaflowmc.leaflow.common.utils.writeVarInt
import io.github.leaflowmc.leaflow.protocol.packets.ClientPacket
import io.github.leaflowmc.leaflow.serialization.minecraft_format.encode
import io.netty.buffer.ByteBuf
import io.netty.channel.ChannelHandlerContext
import io.netty.handler.codec.MessageToByteEncoder
import kotlinx.serialization.KSerializer

class PacketEncoder: MessageToByteEncoder<ClientPacket<*, *>>() {
    override fun encode(ctx: ChannelHandlerContext, msg: ClientPacket<*, *>, out: ByteBuf) {
        out.writeVarInt(msg.getType().id)
        out.encode(
            msg.getType().serializer as KSerializer<ClientPacket<*, *>>,
            msg
        )
    }
}