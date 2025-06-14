package io.github.leaflowmc.leaflow.server.netty

import io.github.leaflowmc.leaflow.common.utils.readVarInt
import io.netty.buffer.ByteBuf
import io.netty.channel.ChannelHandlerContext
import io.netty.handler.codec.ByteToMessageDecoder

class PacketSizeDecoder : ByteToMessageDecoder() {
    override fun decode(
        ctx: ChannelHandlerContext,
        buffer: ByteBuf,
        out: MutableList<Any>
    ) {
        buffer.markReaderIndex()

        val size = buffer.readVarInt()

        if (size > buffer.readableBytes()) {
            buffer.resetReaderIndex()
            return
        }

        out.add(buffer.retainedSlice(buffer.readerIndex(), size))
        buffer.skipBytes(size)
    }
}