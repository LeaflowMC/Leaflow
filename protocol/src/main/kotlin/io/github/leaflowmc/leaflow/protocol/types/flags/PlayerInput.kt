package io.github.leaflowmc.leaflow.protocol.types.flags

import io.github.leaflowmc.leaflow.common.utils.checkBits
import kotlinx.serialization.Serializable

@Serializable
@JvmInline
value class PlayerInput(val flags: Byte) {
    inline val forward
        get() = flags.checkBits(0x1)

    inline val backward
        get() = flags.checkBits(0x2)

    inline val left
        get() = flags.checkBits(0x4)

    inline val right
        get() = flags.checkBits(0x8)

    inline val jump
        get() = flags.checkBits(0x10)

    inline val sneak
        get() = flags.checkBits(0x20)

    inline val sprint
        get() = flags.checkBits(0x40)
}