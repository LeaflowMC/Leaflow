package io.github.leaflowmc.common.utils

import io.netty.buffer.ByteBuf
import java.nio.charset.StandardCharsets

fun ByteBuf.readVarInt(): Int {
    var int = 0
    var count = 0

    var byte: Int
    do {
        byte = readByte().toInt()

        int = int or ((byte and 127) shl count)
        count += 7

        if (count > 35) {
            throw IllegalStateException("VarInt is too big")
        }
    } while ((byte and 128) == 128)

    return int
}

fun ByteBuf.writeVarInt(int: Int) {
    var int = int

    while ((int and -128) != 0) {
        writeByte(int and 127 or 128)
        int = int ushr 7
    }
    writeByte(int)
}

fun ByteBuf.writePrefixedString(string: String) {
    writeVarInt(string.length)
    this.writeBytes(string.toByteArray(StandardCharsets.UTF_8))
}

fun ByteBuf.readPrefixedString(): String {
    val length = readVarInt()
    return readString(length, StandardCharsets.UTF_8)
}