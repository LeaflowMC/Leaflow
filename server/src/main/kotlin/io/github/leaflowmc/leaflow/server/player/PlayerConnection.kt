package io.github.leaflowmc.leaflow.server.player

import io.github.leaflowmc.leaflow.protocol.ProtocolStage
import io.github.leaflowmc.leaflow.protocol.listener.server.ServerPacketListener
import io.github.leaflowmc.leaflow.protocol.packets.ClientPacket
import io.github.leaflowmc.leaflow.server.LeaflowServer
import io.netty.channel.ChannelInboundHandler
import java.security.Key

interface PlayerConnection : ChannelInboundHandler {
    /**
     * Current protocol stage
     */
    val protocol: ProtocolStage
    val server: LeaflowServer

    val encryptionEnabled: Boolean

    /**
     * Setup inbound protocol for the [ProtocolStage]
     */
    fun setProtocol(stage: ProtocolStage, listener: ServerPacketListener)

    /**
     * Setup inbound protocol for the [ProtocolStage] with a default packet listener
     */
    fun setProtocol(stage: ProtocolStage) {
        val listener = server.factory.createServerPacketListenerFor(stage, this)
        setProtocol(stage, listener)
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
    fun sendPacket(packet: ClientPacket<*, *>)
}