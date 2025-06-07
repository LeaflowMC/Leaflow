package io.github.leaflowmc.common.utils

import java.io.EOFException
import java.io.InputStream
import java.nio.charset.StandardCharsets

fun InputStream.readVarInt(): Int {
    var int = 0
    var count = 0

    var byte: Int
    do {
        byte = this.read()
        if (byte == -1) break

        int = int or ((byte and 127) shl count)
        count += 7

        if (count > 35) {
            throw IllegalStateException("VarInt is too big")
        }
    } while ((byte and 128) == 128)

    return int
}
fun InputStream.readByte(): Byte {
    return when (val b = read()) {
        -1 -> throw EOFException("Can't read a byte: end of stream is reached")
        else -> (b and 0xff).toByte()
    }
}
fun InputStream.readUByte(): UByte {
    return readByte().toUByte()
}

fun InputStream.readBoolean() = readByte() != 0.toByte()
fun InputStream.readShort(): Short {
    var short = 0
    repeat(Short.SIZE_BYTES) {
        short = (short shl 8) or readUByte().toInt()
    }

    return (short and 0xffff).toShort()
}

fun InputStream.readInt(): Int {
    var int = 0
    var i = 0
    repeat(Int.SIZE_BYTES) {
        int = int or (readByte().toInt() shl i)
        i += 8
    }
    return int
}

fun InputStream.readLong(): Long {
    var i = 0L
    repeat(Long.SIZE_BYTES) {
        i = (i shl 8) or readByte().toLong()
    }
    return i
}

fun InputStream.readFloat() = java.lang.Float.intBitsToFloat(readInt())
fun InputStream.readDouble() = java.lang.Double.longBitsToDouble(readLong())

fun InputStream.readPrefixedString(): String {
    val size = readVarInt()
    return readFixedString(size)
}

fun InputStream.readFixedString(size: Int): String {
    val bytes = readNBytes(size)
    return bytes.toString(StandardCharsets.UTF_8)
}
