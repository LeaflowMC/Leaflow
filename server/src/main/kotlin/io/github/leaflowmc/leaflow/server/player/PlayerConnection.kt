package io.github.leaflowmc.leaflow.server.player

import io.github.leaflowmc.leaflow.common.types.GameProfile
import io.github.leaflowmc.leaflow.protocol.ProtocolStage
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

    /**
     * The player associated with the connection.
     */
    val player: Deferred<Player>

    val encryptionEnabled: Boolean

    /**
     * Compression threshold. Negative values disable the compression
     *
     * Packets longer than this will be compressed
     *
     * cannot be turned on/off during non-login protocol stage. (wont have any effect)
     *
     * It's fine, however, to change already enabled compression threshold at any moment,
     * because vanilla clients accept compressed packets regardless of their size
     */
    var compressionThreshold: Int

    /**
     * Sends plugin message [msg]
     *
     * @throws IllegalStateException if the message is not registered in `PluginMessages`
     */
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
     * For internal use.
     *
     * Will create a player with the specified [profile]
     *
     * @throws IllegalStateException if the player already exists
     */
    fun finishLogin(profile: GameProfile)

    /**
     * Sends the packet to the player.
     */
    fun sendPacket(packet: ClientPacket<*>)
}