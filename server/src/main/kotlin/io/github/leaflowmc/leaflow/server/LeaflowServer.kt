package io.github.leaflowmc.leaflow.server

import io.github.leaflowmc.leaflow.text.component.TextComponent

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
     * Start the server.
     *
     * Blocks forever until the server exits
     */
    fun start()
}
