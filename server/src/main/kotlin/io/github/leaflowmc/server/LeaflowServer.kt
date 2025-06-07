package io.github.leaflowmc.server

import io.github.leaflowmc.common.utils.readVarInt
import io.github.leaflowmc.protocol.packets.registry.ServerPacketRegistry
import io.github.leaflowmc.serialization.minecraftDecoder
import io.ktor.network.selector.*
import io.ktor.network.sockets.*
import io.ktor.utils.io.*
import io.ktor.utils.io.jvm.javaio.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import kotlinx.serialization.ExperimentalSerializationApi
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
    suspend fun start() = coroutineScope {
        val selectorManager = SelectorManager(Dispatchers.IO)
        val serverSocket = aSocket(selectorManager).tcp().bind(address, port)

        LOGGER.info("Server listening on ${serverSocket.localAddress}")

        while (true) {
            val socket = serverSocket.accept()
            LOGGER.info("Connection from ${socket.remoteAddress}")

            launch(Dispatchers.IO) {
                // TODO add player
                val player = factory.createPlayer(socket)
                val packetListener = factory.createServerPacketListener(player)
                val rChannel = socket.openReadChannel()

                while (true) {
                    val size = rChannel.toInputStream().readVarInt()
                    val stream = rChannel.readByteArray(size).inputStream()
                    val protocolId = stream.readVarInt()
                    val serializer = ServerPacketRegistry[protocolId, player.protocolStage]

                    if (serializer == null) {
                        // TODO player name instead of ip
                        LOGGER.error("Unknown packet id in ${player.protocolStage.name}: 0x${protocolId.toHexString()} (received from ${socket.remoteAddress})")
                        break
                    }
                    val packet = stream.minecraftDecoder().decodeSerializableValue(serializer)
                    try {
                        packet.handle(packetListener)
                    } catch (e: Throwable) {
                        LOGGER.error("Error while handling packet $packet", e)
                        break
                    }
                }
                // TODO disconnect packet
                // TODO remove player
                rChannel.cancel()
                socket.close()
            }
        }
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