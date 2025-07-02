package io.github.leaflowmc.leaflow.registry.types

import io.github.leaflowmc.leaflow.common.types.Identifier
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class BannerPattern(
    @SerialName("asset_id")
    val assetId: Identifier,
    @SerialName("translation_key")
    val translationKey: String
)
