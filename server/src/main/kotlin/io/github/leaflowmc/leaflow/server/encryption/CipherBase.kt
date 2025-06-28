package io.github.leaflowmc.leaflow.server.encryption

import io.netty.buffer.ByteBuf
import io.netty.channel.ChannelHandlerContext
import javax.crypto.Cipher

class CipherBase(private val cipher: Cipher) {
    private var heapIn = ByteArray(0)
    private var heapOut = ByteArray(0)

    fun ByteBuf.toByteArray(): ByteArray {
        val len = readableBytes()

        if (heapIn.size < len) {
            heapIn = ByteArray(len)
        }

        readBytes(heapIn, 0, len)

        return heapIn
    }

    fun decipher(ctx: ChannelHandlerContext, buffer: ByteBuf): ByteBuf {
        val len = buffer.readableBytes()

        val heapIn = buffer.toByteArray()
        val outBuffer = ctx.alloc().heapBuffer(cipher.getOutputSize(len))

        cipher.update(heapIn, 0, len, outBuffer.array(), outBuffer.arrayOffset())
            .also(outBuffer::writerIndex)

        return outBuffer
    }

    fun encipher(input: ByteBuf, output: ByteBuf) {
        val len = input.readableBytes()
        val heapIn = input.toByteArray()

        val outputLen = cipher.getOutputSize(len)

        if (heapOut.size < outputLen) {
            heapOut = ByteArray(outputLen)
        }

        output.writeBytes(heapOut, 0, cipher.update(heapIn, 0, len, heapOut))
    }
}