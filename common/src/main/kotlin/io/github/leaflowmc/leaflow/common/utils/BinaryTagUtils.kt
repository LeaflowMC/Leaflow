package io.github.leaflowmc.leaflow.common.utils

import net.kyori.adventure.nbt.*

fun Any.toBinaryTag(): BinaryTag {
    return when (this) {
        is Byte -> ByteBinaryTag.byteBinaryTag(this)
        is Short -> ShortBinaryTag.shortBinaryTag(this)
        is Int -> IntBinaryTag.intBinaryTag(this)
        is Long -> LongBinaryTag.longBinaryTag(this)
        is Float -> FloatBinaryTag.floatBinaryTag(this)
        is Double -> DoubleBinaryTag.doubleBinaryTag(this)
        is String -> StringBinaryTag.stringBinaryTag(this)

        else -> throw IllegalArgumentException("NBT format doesn't support this primitive")
    }
}
