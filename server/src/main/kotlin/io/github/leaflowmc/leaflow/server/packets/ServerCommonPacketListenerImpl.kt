package io.github.leaflowmc.leaflow.server.packets

import io.github.leaflowmc.leaflow.common.api.Disposable
import io.github.leaflowmc.leaflow.common.api.Tickable
import io.github.leaflowmc.leaflow.protocol.packets.common.ClientboundKeepAlivePacket
import io.github.leaflowmc.leaflow.protocol.packets.common.ServerboundKeepAlivePacket
import io.github.leaflowmc.leaflow.protocol.packets.ping.ClientboundPingPacket
import io.github.leaflowmc.leaflow.protocol.packets.ping.ServerboundPongPacket
import io.github.leaflowmc.leaflow.server.packets.api.LeaflowServerCommonPacketListener
import io.github.leaflowmc.leaflow.server.player.PlayerConnection
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.Deferred
import org.apache.logging.log4j.LogManager
import kotlin.concurrent.atomics.AtomicInt
import kotlin.concurrent.atomics.ExperimentalAtomicApi
import kotlin.time.Clock
import kotlin.time.Duration
import kotlin.time.ExperimentalTime
import kotlin.time.Instant

@OptIn(ExperimentalTime::class, ExperimentalAtomicApi::class)
abstract class ServerCommonPacketListenerImpl : LeaflowServerCommonPacketListener, Tickable, Disposable {
    companion object {
        private val LOGGER = LogManager.getLogger()
        const val KEEP_ALIVE_DELAY = 15 * 1000
    }

    abstract val playerConnection: PlayerConnection

    private var lastKeepAlive: Long = System.currentTimeMillis()
    private var keepAliveAnswered: Boolean = true

    private val pingDeferred = mutableMapOf<Int, Pair<Instant, CompletableDeferred<Duration>>>()
    private val pingCounter = AtomicInt(0)

    abstract fun getPingPacket(timestamp: Int): ClientboundPingPacket<*, *>
    abstract fun getKeepAlivePacket(id: Long): ClientboundKeepAlivePacket<*, *>

    final override fun sendPing(): Deferred<Duration> {
        val id = pingCounter.fetchAndAdd(1)

        playerConnection.sendPacket(getPingPacket(id))

        val deferred = CompletableDeferred<Duration>()
        pingDeferred[id] = Clock.System.now() to deferred

        return deferred
    }

    override fun tick() {
        keepConnectionAlive()
    }

    private fun keepConnectionAlive() {
        val lastKeepAlive = this.lastKeepAlive
        val keepAliveAnswered = this.keepAliveAnswered

        val now = System.currentTimeMillis()

        if (now - lastKeepAlive > KEEP_ALIVE_DELAY) {
            if (keepAliveAnswered) {
                val id = System.currentTimeMillis()
                playerConnection.sendPacket(getKeepAlivePacket(id))

                this.lastKeepAlive = id
                this.keepAliveAnswered = false
            } else {
                LOGGER.warn("ALARM, CLIENT DIDN'T RESPOND TO KEEP ALIVE")
                // TODO: disconnect
            }
        }
    }

    final override fun keepAlive(packet: ServerboundKeepAlivePacket<*, *>) {
        if (!this.keepAliveAnswered && this.lastKeepAlive == packet.id) {
            this.keepAliveAnswered = true
        } else {
            LOGGER.warn("received unexpected or invalid keep alive")
            // TODO disconnect
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
    }
}