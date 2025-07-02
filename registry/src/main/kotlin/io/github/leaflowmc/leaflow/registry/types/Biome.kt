package io.github.leaflowmc.leaflow.registry.types

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Biome(
    @SerialName("has_precipitation")
    val hasPrecipitation: Boolean,
    val temperature: Float,
    @SerialName("down_fall")
    val downFall: Float,
    val effects: BiomeEffects,
    @SerialName("temperature_modifier")
    val temperatureModifier: TemperatureModifier
) {
}

enum class TemperatureModifier {
    NONE,
    FROZEN;
}
