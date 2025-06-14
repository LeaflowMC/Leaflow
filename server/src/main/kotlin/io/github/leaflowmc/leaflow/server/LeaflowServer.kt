package io.github.leaflowmc.leaflow.server

interface LeaflowServer {
    val address: String
    val port: Int
    val factory: LeaflowFactory

    /**
     * Start the server.
     *
     * Blocks forever until the server exits
     */
    fun start()
}
