package io.github.leaflowmc.leaflow.protocol.types

import kotlinx.serialization.Serializable

@Serializable
@JvmInline
value class Position(val value: Long) {
    constructor(x: Int, z: Int, y: Int) : this(
        ((x.toLong() and 0x3FFFFFF) shl 38) or
                ((z.toLong() and 0x3FFFFFF) shl 12) or
                (y.toLong() and 0xFFF)
    )

    inline val x: Int
        get() = (value shr 38).toInt()
    inline val z: Int
        get() = (value shl 26 shr 38).toInt()
    inline val y: Int
        get() = (value shl 52 shr 52).toInt()
}