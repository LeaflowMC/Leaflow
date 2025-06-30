package io.github.leaflowmc.leaflow.server.packets.plugin_message

import io.github.leaflowmc.leaflow.server.player.PlayerConnection
import kotlinx.serialization.Serializable

@Serializable
data class BrandPluginMessage(
    val brand: String
) : PluginMessage {
    override fun handle(connection: PlayerConnection) {
    }
}