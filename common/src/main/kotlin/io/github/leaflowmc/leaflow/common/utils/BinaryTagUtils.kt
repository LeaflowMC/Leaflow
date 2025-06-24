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

/**
 * @return If binary tag is a primitive, returns the value
 *
 * null otherwise
 */
fun BinaryTag.getPrimitive(): Any? {
    return when (this) {
        is ByteBinaryTag -> value()
        is ShortBinaryTag -> value()
        is IntBinaryTag -> value()
        is LongBinaryTag -> value()
        is FloatBinaryTag -> value()
        is DoubleBinaryTag -> value()
        is StringBinaryTag -> value()

        else -> null
    }
}