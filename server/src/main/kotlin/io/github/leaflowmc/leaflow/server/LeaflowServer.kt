package io.github.leaflowmc.leaflow.server

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
    val authEnabled: Boolean
    val keyPair: KeyPair

    /**
     * Start the server.
     *
     * Blocks forever until the server exits
     */
    fun start()
}
