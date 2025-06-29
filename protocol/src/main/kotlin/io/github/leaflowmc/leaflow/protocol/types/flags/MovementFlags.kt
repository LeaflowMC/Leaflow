package io.github.leaflowmc.leaflow.protocol.types.flags

import io.github.leaflowmc.leaflow.common.utils.checkBits
import kotlinx.serialization.Serializable

@Serializable
@JvmInline
value class MovementFlags(val flags: Byte) {
    inline val isOnGround: Boolean
        get() = flags.checkBits(0x1)

    inline val isPushingWall: Boolean
        get() = flags.checkBits(0x2)
}