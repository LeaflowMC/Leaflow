package io.github.leaflowmc.leaflow.registry.types

import io.github.leaflowmc.leaflow.text.component.TextComponent
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Enchantment(
    val description: TextComponent,
    @SerialName("supported_items")
    val supportedItems: List<Nothing>, // TODO: holderSet<Item>
    @SerialName("primary_items")
    val primaryItems: List<Nothing>?, // TODO: HolderSet<Item>
    val weight: Int,
    @SerialName("max_level")
    val maxLevel: Int,
    @SerialName("min_cost")
    val minCost: Cost,
    @SerialName("max_cost")
    val maxCost: Cost,
    @SerialName("anvil_cost")
    val anvilCost: Int,
    val slots: List<Nothing> // TODO: list of Equipment slot group
) {
    @Serializable
    data class Cost(
        val base: Int,
        @SerialName("per_level_above_first")
        val perLevelAboveFirst: Int
    )
}