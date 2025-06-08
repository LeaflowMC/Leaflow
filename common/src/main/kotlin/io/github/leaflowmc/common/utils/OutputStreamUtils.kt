package io.github.leaflowmc.common.utils

import java.io.OutputStream
import java.nio.charset.StandardCharsets

fun OutputStream.writeVarInt(int: Int) {
    var int = int

    while ((int and -128) != 0) {
        write(int and 127 or 128)
        int = int ushr 7
    }
    write(int)
}

fun OutputStream.writeByte(byte: Byte) = write(byte.toUByte().toInt())
fun OutputStream.writeUByte(byte: UByte) = write(byte.toInt())

fun OutputStream.writeBoolean(bool: Boolean) {
    if (bool)
        writeUByte(1.toUByte())
    else
        writeUByte(0.toUByte())
}

fun OutputStream.writeShort(short: Short) {
    var short = java.lang.Short.reverseBytes(short).toInt()
    repeat(Short.SIZE_BYTES) {
        write(short and 0xff)
        short = short ushr 8
    }
}

fun OutputStream.writeInt(int: Int) {
    var int = Integer.reverseBytes(int)
    repeat(Int.SIZE_BYTES) {
        write(int and 0xff)
        int = int ushr 8
    }
}

fun OutputStream.writeLong(long: Long) {
    var long = java.lang.Long.reverseBytes(long)
    repeat(Long.SIZE_BYTES) {
        write(long.toInt() and 0xff)
        long = long ushr 8
    }
}

fun OutputStream.writeFloat(float: Float) {
    writeInt(java.lang.Float.floatToIntBits(float))
}

fun OutputStream.writeDouble(double: Double) {
    writeLong(java.lang.Double.doubleToLongBits(double))
}

fun OutputStream.writePrefixedString(string: String) {
    writeVarInt(string.length)
    writeString(string)
}
fun OutputStream.writeString(string: String) {
    write(string.toByteArray(StandardCharsets.UTF_8))
}