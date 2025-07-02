package io.github.leaflowmc.leaflow.registry.types

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class DamageType(
    @SerialName("message_id")
    val messageId: String,
    val scaling: DamageScaling,
    val exhaustion: Float,
    val effects: DamageEffects = DamageEffects.HURT,
    @SerialName("death_message_type")
    val deathMessageType: DeathMessageType = DeathMessageType.DEFAULT
) {
}

enum class DamageScaling {
    NEVER,
    WHEN_CAUSED_BY_LIVING_NON_PLAYER,
    ALWAYS;
}

enum class DamageEffects {
    HURT,
    THORNS,
    DROWNING,
    BURNING,
    POKING,
    FREEZING;
}

enum class DeathMessageType {
    DEFAULT,
    FALL_VARIANTS,
    INTENTIONAL_GAME_DESIGN;
}
