package io.github.leaflowmc.leaflow.server.player

import io.github.leaflowmc.leaflow.protocol.ProtocolStage
import io.github.leaflowmc.leaflow.protocol.listener.server.ServerPacketListener
import io.github.leaflowmc.leaflow.protocol.packets.ClientPacket
import io.github.leaflowmc.leaflow.server.LeaflowServer
import io.netty.channel.ChannelInboundHandler
import kotlinx.coroutines.Deferred
import java.security.Key
import kotlin.time.Duration

interface PlayerConnection : ChannelInboundHandler {
    /**
     * Current protocol stage
     */
    val protocol: ProtocolStage
    val server: LeaflowServer

    val encryptionEnabled: Boolean

    /**
     * Sends a ping, if current protocol stage has a clientbound ping packet,
     * and returns a deferred duration
     *
     * Otherwise, returns null
     */
    fun sendPing(): Deferred<Duration>?

    /**
     * Setup inbound protocol for the [ProtocolStage]
     */
    fun setInboundProtocol(stage: ProtocolStage, listener: ServerPacketListener)

    /**
     * Setup outbound protocol for the [ProtocolStage]
     */
    fun setOutboundProtocol(stage: ProtocolStage)

    /**
     * Setup inbound protocol for the [ProtocolStage] with a default packet listener
     */
    fun setInboundProtocol(stage: ProtocolStage) {
        val listener = server.factory.createServerPacketListenerFor(stage, this)
        setInboundProtocol(stage, listener)
    }

    /**
     * Sets up the handlers for encryption with the specified key
     *
     * Enables [encryptionEnabled] boolean flag
     */
    fun setEncryptionKey(key: Key)

    /**
     * Sends the packet to the player.
     */
    fun sendPacket(packet: ClientPacket<*>)
}