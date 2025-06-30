package io.github.leaflowmc.leaflow.server.packets

import io.github.leaflowmc.leaflow.common.api.Disposable
import io.github.leaflowmc.leaflow.common.api.Tickable
import io.github.leaflowmc.leaflow.protocol.packets.common.ClientboundKeepAlivePacket
import io.github.leaflowmc.leaflow.protocol.packets.common.ServerboundKeepAlivePacket
import io.github.leaflowmc.leaflow.protocol.packets.common.ServerboundPluginMessagePacket
import io.github.leaflowmc.leaflow.protocol.packets.ping.ClientboundPingPacket
import io.github.leaflowmc.leaflow.protocol.packets.ping.ServerboundPongPacket
import io.github.leaflowmc.leaflow.serialization.minecraft_format.decode
import io.github.leaflowmc.leaflow.server.constants.TextConstants
import io.github.leaflowmc.leaflow.server.packets.api.LeaflowServerCommonPacketListener
import io.github.leaflowmc.leaflow.server.player.PlayerConnection
import io.github.leaflowmc.leaflow.text.component.TranslatedTextComponent
import io.netty.buffer.Unpooled
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

    final override fun sendPing(): Deferred<Duration> {
        val id = pingCounter.fetchAndAdd(1)

        playerConnection.sendPacket(ClientboundPingPacket(id))

        val deferred = CompletableDeferred<Duration>()
        pingDeferred[id] = Clock.System.now() to deferred

        return deferred
    }

    override fun tick() {
        keepConnectionAlive()
    }

    private fun keepConnectionAlive() {
        val now = System.currentTimeMillis()

        if (now - this.lastKeepAlive > KEEP_ALIVE_DELAY) {
            if (this.keepAliveAnswered) {
                playerConnection.sendPacket(ClientboundKeepAlivePacket(now))

                this.lastKeepAlive = now
                this.keepAliveAnswered = false
            } else {
                playerConnection.disconnect(
                    TranslatedTextComponent(TextConstants.DISCONNECT_TIMED_OUT)
                )
            }
        }
    }

    final override fun keepAlive(packet: ServerboundKeepAlivePacket) {
        if (!this.keepAliveAnswered && this.lastKeepAlive == packet.id) {
            this.keepAliveAnswered = true
        } else {
            playerConnection.disconnect(
                TranslatedTextComponent(TextConstants.DISCONNECT_TIMED_OUT)
            )
        }
    }

    final override fun pong(packet: ServerboundPongPacket) {
        val (lastPing, deferred) = pingDeferred.remove(packet.id) ?: run {
            LOGGER.warn("invalid pong packet with id: ${packet.id}")
            return
        }

        val now = Clock.System.now()
        deferred.complete(now - lastPing)
    }

    override fun pluginMessage(packet: ServerboundPluginMessagePacket) {
        val seri = playerConnection.server.pluginMessages[packet.id]

        if (seri == null) {
            LOGGER.warn("received unknown plugin message: ${packet.id}}")
            return
        }

        val buffer = Unpooled.wrappedBuffer(packet.data)
        val msg = buffer.decode(seri)
        msg.handle(playerConnection)
    }

    override fun dispose() {
    }
}