package io.github.leaflowmc.leaflow.protocol.types.flags

import io.github.leaflowmc.leaflow.common.utils.checkBits
import kotlinx.serialization.Serializable

@Serializable
@JvmInline
value class TeleportFlags(val flags: Int) {
    inline val isRelativeX: Boolean
        get() = flags.checkBits(0x1)

    inline val isRelativeY: Boolean
        get() = flags.checkBits(0x2)

    inline val isRelativeZ: Boolean
        get() = flags.checkBits(0x4)

    inline val isRelativeYaw: Boolean
        get() = flags.checkBits(0x8)

    inline val isRelativePitch: Boolean
        get() = flags.checkBits(0x10)

    inline val isRelativeVelocityX: Boolean
        get() = flags.checkBits(0x20)

    inline val isRelativeVelocityY: Boolean
        get() = flags.checkBits(0x40)

    inline val isRelativeVelocityZ: Boolean
        get() = flags.checkBits(0x80)

    inline val isRotateBeforeVelocity: Boolean
        get() = flags.checkBits(0x100)
}