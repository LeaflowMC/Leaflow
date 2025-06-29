package io.github.leaflowmc.leaflow.protocol.types

import kotlinx.serialization.Serializable

@Serializable
data class DeathLocation(
    val dimensionName: String,
    val location: Position
)