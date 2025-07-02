package io.github.leaflowmc.leaflow.registry.types

import io.github.leaflowmc.leaflow.common.types.Identifier
import io.github.leaflowmc.leaflow.text.component.TextComponent
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TrimPattern(
    @SerialName("asset_id")
    val assetId: Identifier,
    val description: TextComponent,
    val decal: Boolean
)
