package io.github.leaflowmc.leaflow.common.utils

import kotlin.experimental.and

@Suppress("NOTHING_TO_INLINE")
inline fun Byte.checkBits(mask: Byte): Boolean = (this and mask) != 0.toByte()

@Suppress("NOTHING_TO_INLINE")
inline fun Int.checkBits(mask: Int): Boolean = (this and mask) != 0

@Suppress("NOTHING_TO_INLINE")
inline fun Boolean.toInt(): Int = if (this) 1 else 0

@Suppress("NOTHING_TO_INLINE")
inline fun Boolean.toByte(): Byte = if (this) 1 else 0

fun booleansToFlags(vararg boolean: Boolean): Int {
    return boolean.foldIndexed(0) { shift, l, r ->
        l or (r.toInt() shl shift)
    }
}
