package io.github.leaflowmc.leaflow.server.packets.api

import io.github.leaflowmc.leaflow.protocol.listener.server.ServerCommonPacketListener
import kotlinx.coroutines.Deferred
import kotlin.time.Duration

interface LeaflowServerCommonPacketListener : ServerCommonPacketListener {
    /**
     * Sends a ping request to player and returns a deferred ping duration
     */
    fun sendPing(): Deferred<Duration>
}