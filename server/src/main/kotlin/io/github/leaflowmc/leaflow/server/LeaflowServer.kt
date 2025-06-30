package io.github.leaflowmc.leaflow.server

import io.github.leaflowmc.leaflow.server.packets.plugin_message.PluginMessages
import io.github.leaflowmc.leaflow.text.component.TextComponent
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
     * Start the server.
     *
     * Blocks forever until the server exits
     */
    fun start()
}
