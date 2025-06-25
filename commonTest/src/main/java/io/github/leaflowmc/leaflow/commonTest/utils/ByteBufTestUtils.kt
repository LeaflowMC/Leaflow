package io.github.leaflowmc.leaflow.commonTest.utils

import io.netty.buffer.ByteBuf
import io.netty.buffer.Unpooled
import java.io.ByteArrayOutputStream

inline fun byteBufBytes(block: ByteBuf.() -> Unit): ByteArray {
    val output = ByteArrayOutputStream()

    Unpooled.buffer()
        .apply(block)
        .run {
            readBytes(output, readableBytes())
        }
        .release()

    return output.toByteArray()
}