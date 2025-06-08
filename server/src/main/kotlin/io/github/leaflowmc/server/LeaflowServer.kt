package io.github.leaflowmc.server

import io.github.leaflowmc.common.utils.readVarInt
import io.github.leaflowmc.protocol.packets.ClientPacket
import io.github.leaflowmc.protocol.packets.registry.ServerPacketRegistry
import io.github.leaflowmc.serialization.minecraftDecoder
import io.github.leaflowmc.serialization.minecraftEncoder
import io.ktor.network.selector.*
import io.ktor.network.sockets.*
import io.ktor.utils.io.*
import io.ktor.utils.io.jvm.javaio.*
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.selects.select
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.InternalSerializationApi
import kotlinx.serialization.KSerializer
import kotlinx.serialization.serializer
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger
import java.io.ByteArrayOutputStream
import java.io.OutputStream
import kotlin.reflect.KClass

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
        val selectorManager = SelectorManager(Dispatchers.IO)
        val serverSocket = aSocket(selectorManager).tcp().bind(address, port)

        LOGGER.info("Server listening on ${serverSocket.localAddress}")

        while (true) {
            val socket = serverSocket.accept()
            LOGGER.info("Connection from ${socket.remoteAddress}")

            val packetsChannel = Channel<ClientPacket>(Channel.BUFFERED)
            val player = factory.createPlayer(socket, packetsChannel)
            val packetListener = factory.createServerPacketListener(player)

            launch {
                val readJob = launch(Dispatchers.IO) {
                    // TODO add player
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
                    rChannel.cancel()
                }

                val writeJob = launch(Dispatchers.IO) {
                    val writeChannel = socket.openWriteChannel(false)

                    for (packet: ClientPacket in packetsChannel) {
                        val outputStream = ByteArrayOutputStream()
                        outputStream.minecraftEncoder().encodeSerializableValue(
                            packet::class.serializer() as KSerializer<in ClientPacket>,
                            packet
                        )
                        writeChannel.writeByteArray(outputStream.toByteArray())
                        writeChannel.flush()
                    }
                    writeChannel.flushAndClose()
                }

                select {
                    readJob.onJoin {
                        readJob.join()
                    }
                    writeJob.onJoin {
                        writeJob.join()
                    }
                }
                withContext(NonCancellable) {
                    // TODO remove player
                    socket.close()
                }
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