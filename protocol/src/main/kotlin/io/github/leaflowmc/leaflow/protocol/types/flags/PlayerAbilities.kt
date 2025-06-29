package io.github.leaflowmc.leaflow.protocol.types.flags

import io.github.leaflowmc.leaflow.common.utils.booleansToFlags
import io.github.leaflowmc.leaflow.common.utils.checkBits
import kotlinx.serialization.Serializable

@Serializable
@JvmInline
value class PlayerAbilities(val flags: Byte) {
    constructor(
        invulnerable: Boolean,
        flying: Boolean,
        allowFlying: Boolean,
        creativeMode: Boolean,
    ) : this(
        booleansToFlags(invulnerable, flying, allowFlying, creativeMode).toByte()
    )

    inline val invulnerable: Boolean
        get() = flags.checkBits(0x1)

    inline val flying: Boolean
        get() = flags.checkBits(0x2)

    inline val allowFlying: Boolean
        get() = flags.checkBits(0x4)

    inline val creativeMode: Boolean
        get() = flags.checkBits(0x8)
}