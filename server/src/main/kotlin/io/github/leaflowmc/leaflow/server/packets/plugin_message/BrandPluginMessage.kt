package io.github.leaflowmc.leaflow.server.packets.plugin_message

import io.github.leaflowmc.leaflow.server.event.ClientBrandEvent
import io.github.leaflowmc.leaflow.server.player.PlayerConnection
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.Serializable

@OptIn(ExperimentalCoroutinesApi::class)
@Serializable
data class BrandPluginMessage(
    val brand: String
) : PluginMessage {
    override fun handle(connection: PlayerConnection) {
        val event = ClientBrandEvent(connection, brand)
        connection.server.events.tryEmit(event)
    }
}