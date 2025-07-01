package io.github.leaflowmc.leaflow.server.netty

import io.github.leaflowmc.leaflow.common.utils.readVarInt
import io.netty.buffer.ByteBuf
import io.netty.channel.ChannelHandlerContext
import io.netty.handler.codec.ByteToMessageDecoder
import java.util.zip.Inflater

class CompressionDecoder() : ByteToMessageDecoder() {
    private val inflater = Inflater()

    override fun decode(
        ctx: ChannelHandlerContext,
        buffer: ByteBuf,
        output: MutableList<Any>
    ) {
        val length = buffer.readVarInt()

        if (length == 0) {
            output.add(buffer.retainedSlice(buffer.readerIndex(), buffer.readableBytes()))
            buffer.skipBytes(buffer.readableBytes())
            return
        }

        check(length <= 8388608) { "uncompressed data is longer than the limit" }

        val bytes = ByteArray(buffer.readableBytes())
        buffer.readBytes(bytes)

        inflater.setInput(bytes)

        val outBuf = ctx.alloc().buffer(length)
        val outBytes = ByteArray(1024)

        while(!inflater.finished()) {
            val size = inflater.inflate(outBytes)
            outBuf.writeBytes(outBytes, 0, size)
        }
        output.add(outBuf)
    }
}