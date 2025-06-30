package io.github.leaflowmc.leaflow.server

import io.github.leaflowmc.leaflow.server.event.Event
import io.github.leaflowmc.leaflow.server.packets.plugin_message.PluginMessages
import io.github.leaflowmc.leaflow.text.component.TextComponent
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import java.security.KeyPair

interface LeaflowServer {
    val address: String
    val port: Int
    val factory: LeaflowFactory

    /**
     * Message Of The Day
     *
     * the description of the server in server list
     */
    val motd: TextComponent?

    /**
     * Brand of this server, sent in `ClientboundPluginMessagePacket`
     * as a `BrandPluginMessage`
     *
     * @see io.github.leaflowmc.leaflow.server.packets.plugin_message.BrandPluginMessage
     */
    val brand: String

    val authEnabled: Boolean
    val keyPair: KeyPair

    /**
     * Plugin messages "registry"
     */
    val pluginMessages: PluginMessages

    /**
     * A mutable shared flow of events. Use to send or collect events
     *
     * Example of subscribing to an event:
     * ```
     * events.filterIsInstance<ClientBrandEvent>()
     *     .collect { event ->
     *         val player = event.player.await()
     *
     *         println("received ${player.profile.name}'s brand: ${event.brand}")
     *     }
     * ```
     */
    val events: MutableSharedFlow<Event>

    /**
     * Start the server.
     *
     * Blocks forever until the server exits
     */
    fun start()
}
