package io.github.leaflowmc.leaflow.registry.types

import io.github.leaflowmc.leaflow.common.types.Identifier
import io.github.leaflowmc.leaflow.common.utils.VarInt
import io.github.leaflowmc.leaflow.text.component.TextComponent
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PaintingVariant(
    val width: VarInt,
    val height: VarInt,
    @SerialName("asset_id")
    val assetId: Identifier,
    val title: TextComponent?,
    val author: TextComponent?
)
