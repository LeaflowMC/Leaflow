package io.github.leaflowmc.server

import kotlinx.coroutines.coroutineScope
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.InternalSerializationApi
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger

@OptIn(ExperimentalSerializationApi::class, ExperimentalStdlibApi::class)
class LeaflowServer(
    val address: String,
    val port: Int,
    val factory: LeaflowFactory
) {
    companion object {
        val LOGGER: Logger = LogManager.getLogger()
    }

    /**
     * Main loop of ktor, processing packets.
     */
    @OptIn(InternalSerializationApi::class)
    suspend fun start() = coroutineScope {
    }

    class Builder {
        var address = "0.0.0.0"
        var port = 25565
        var factory: LeaflowFactory = LeaflowFactoryImpl

        fun build(): LeaflowServer {
            return LeaflowServer(address, port, factory)
        }
    }
}

fun createLeaflowServer(block: LeaflowServer.Builder.() -> Unit = {}): LeaflowServer {
    return LeaflowServer.Builder().apply(block).build()
}