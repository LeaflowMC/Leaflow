package io.github.leaflowmc.leaflow.common.types

import io.github.leaflowmc.leaflow.common.serializer.UUIDAsLongs
import io.github.leaflowmc.leaflow.common.utils.UUIDUtils
import kotlinx.serialization.Contextual
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GameProfile(
    @Contextual
    @SerialName("id")
    val uuid: UUIDAsLongs,
    val name: String,
    val properties: List<Property>
) {
    companion object {
        fun forOfflinePlayer(name: String) = GameProfile(UUIDUtils.createOffline(name), name, listOf())
    }

    @Serializable
    data class Property(
        val name: String,
        val value: String,
        val signature: String? = null
    )
}
