package io.github.leaflowmc.leaflow.server.player

import io.github.leaflowmc.leaflow.protocol.ProtocolStage
import io.github.leaflowmc.leaflow.protocol.listener.server.ServerPacketListener
import io.github.leaflowmc.leaflow.protocol.packets.ClientPacket
import io.github.leaflowmc.leaflow.server.LeaflowServer
import io.github.leaflowmc.leaflow.server.packets.plugin_message.PluginMessage
import io.github.leaflowmc.leaflow.text.component.TextComponent
import io.netty.channel.ChannelInboundHandler
import kotlinx.coroutines.Deferred
import java.security.Key
import kotlin.time.Duration

interface PlayerConnection : ChannelInboundHandler {
    val server: LeaflowServer

    val encryptionEnabled: Boolean

    fun <T : PluginMessage> sendPluginMessage(msg: T)

    /**
     * Sends disconnect packet and closes the channel
     */
    fun disconnect(reason: TextComponent)

    /**
     * Sends a ping, if current protocol stage has a clientbound ping packet,
     * and returns a deferred duration
     *
     * Otherwise, returns null
     */
    fun sendPing(): Deferred<Duration>?

    /**
     * Setup outbound protocol for the [ProtocolStage]
     */
    fun setOutboundProtocol(stage: ProtocolStage)

    /**
     * Setup inbound protocol for the [ProtocolStage] with a default packet listener
     */
    fun setInboundProtocol(stage: ProtocolStage)

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