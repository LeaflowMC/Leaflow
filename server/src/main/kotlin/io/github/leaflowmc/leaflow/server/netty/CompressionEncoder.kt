package io.github.leaflowmc.leaflow.server.netty

import io.github.leaflowmc.leaflow.common.utils.writeVarInt
import io.netty.buffer.ByteBuf
import io.netty.channel.ChannelHandlerContext
import io.netty.handler.codec.MessageToByteEncoder
import java.util.zip.Deflater
import kotlin.properties.Delegates

class CompressionEncoder(threshold: Int) : MessageToByteEncoder<ByteBuf>() {
    var threshold by Delegates.vetoable(threshold) { _, _, new -> new >= 0 }

    private val deflater = Deflater()

    override fun encode(ctx: ChannelHandlerContext, buffer: ByteBuf, output: ByteBuf) {
        val length = buffer.readableBytes()

        if (length < threshold) {
            output.writeVarInt(0)
            output.writeBytes(buffer)
        } else {
            output.writeVarInt(length)

            val bytes = ByteArray(buffer.readableBytes())
            buffer.readBytes(bytes)

            deflater.setInput(bytes)
            deflater.finish()

            val outBytes = ByteArray(1024)

            while (!deflater.finished()) {
                val size = deflater.deflate(outBytes)
                output.writeBytes(outBytes, 0, size)
            }
            deflater.reset()
        }
    }
}