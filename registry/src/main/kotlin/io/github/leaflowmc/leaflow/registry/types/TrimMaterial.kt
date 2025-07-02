package io.github.leaflowmc.leaflow.registry.types

import io.github.leaflowmc.leaflow.text.component.TextComponent
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TrimMaterial(
    @SerialName("asset_name")
    val assetName: String,
    @SerialName("override_armor_assets")
    val overrides: Map<String, String>,
    val description: TextComponent
)
