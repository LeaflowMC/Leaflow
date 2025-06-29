package io.github.leaflowmc.leaflow.server.packets

import io.github.leaflowmc.leaflow.protocol.packets.common.ClientboundConfigurationKeepAlivePacket
import io.github.leaflowmc.leaflow.protocol.packets.common.ServerboundKeepAlivePacket
import io.github.leaflowmc.leaflow.protocol.packets.ping.ClientboundPingPacket
import io.github.leaflowmc.leaflow.protocol.packets.ping.ServerboundPongPacket
import io.github.leaflowmc.leaflow.server.packets.api.LeaflowServerCommonPacketListener
import io.github.leaflowmc.leaflow.server.player.PlayerConnection
import kotlinx.coroutines.*
import kotlinx.coroutines.selects.select
import org.apache.logging.log4j.LogManager
import kotlin.concurrent.atomics.AtomicInt
import kotlin.concurrent.atomics.ExperimentalAtomicApi
import kotlin.time.Clock
import kotlin.time.Duration
import kotlin.time.Duration.Companion.seconds
import kotlin.time.ExperimentalTime
import kotlin.time.Instant

@OptIn(ExperimentalTime::class, ExperimentalAtomicApi::class)
abstract class ServerCommonPacketListenerImpl : LeaflowServerCommonPacketListener {
    companion object {
        private val LOGGER = LogManager.getLogger()
        const val KEEP_ALIVE_DELAY = 18
        const val KEEP_ALIVE_WAIT = 15
    }

    abstract val playerConnection: PlayerConnection

    private val keepAliveMap = mutableMapOf<Long, CompletableDeferred<Unit>>()
    private val pingDeferred = mutableMapOf<Int, Pair<Instant, CompletableDeferred<Duration>>>()
    private val pingCounter = AtomicInt(0)

    private val scope = CoroutineScope(Dispatchers.IO)

    init {
        scope.launch {
            keepAliveLoop()
        }
    }

    abstract fun getPingPacket(timestamp: Int): ClientboundPingPacket<*, *>

    final override fun sendPing(): Deferred<Duration> {
        val id = pingCounter.fetchAndAdd(1)

        playerConnection.sendPacket(getPingPacket(id))

        val deferred = CompletableDeferred<Duration>()
        pingDeferred[id] = Clock.System.now() to deferred

        return deferred
    }

    final override fun keepAlive(packet: ServerboundKeepAlivePacket<*, *>) {
        val completed = keepAliveMap[packet.id]?.complete(Unit)

        if(completed == false) {
            LOGGER.warn("duplicate keep alive")
        } else if (completed == null) {
            LOGGER.warn("invalid keep alive id")
        }
    }

    final override fun pong(packet: ServerboundPongPacket<*, *>) {
        val (lastPing, deferred) = pingDeferred.remove(packet.id) ?: run {
            LOGGER.warn("invalid pong packet with id: ${packet.id}")
            return
        }

        val now = Clock.System.now()
        deferred.complete(now - lastPing)
    }

    override fun dispose() {
        scope.cancel("Listener is disposed")
    }

    private suspend fun CoroutineScope.keepAliveLoop() {
        while (true) {
            launch {
                val id = Clock.System.now().toEpochMilliseconds()

                playerConnection.sendPacket(
                    ClientboundConfigurationKeepAlivePacket(id)
                )

                val deferred = CompletableDeferred<Unit>(coroutineContext.job)
                keepAliveMap[id] = deferred

                select {
                    launch {
                        delay(KEEP_ALIVE_WAIT.seconds)
                    }.onJoin {
                        LOGGER.warn("ALARM, CLIENT DIDN'T RESPOND TO KEEP ALIVE")
                        // TODO: disconnect
                    }
                    deferred.onAwait {}
                }
            }

            delay(KEEP_ALIVE_DELAY.seconds)
        }
    }
}