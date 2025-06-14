package io.github.leaflowmc.leaflow.server.netty

import io.github.leaflowmc.leaflow.common.utils.writeVarInt
import io.netty.buffer.ByteBuf
import io.netty.channel.ChannelHandlerContext
import io.netty.handler.codec.MessageToByteEncoder

class PacketSizeEncoder : MessageToByteEncoder<ByteBuf>() {
    override fun encode(
        ctx: ChannelHandlerContext,
        msg: ByteBuf,
        out: ByteBuf
    ) {
        out.writeVarInt(msg.readableBytes())
        out.writeBytes(msg)
    }
}
