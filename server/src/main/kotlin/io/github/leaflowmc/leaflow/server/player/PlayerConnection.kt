package io.github.leaflowmc.leaflow.server.player

import io.github.leaflowmc.leaflow.protocol.ProtocolStage
import io.github.leaflowmc.leaflow.protocol.packets.ClientPacket
import io.github.leaflowmc.leaflow.server.LeaflowServer
import io.netty.channel.ChannelInboundHandler
import java.security.Key

interface PlayerConnection : ChannelInboundHandler {
    /**
     * Property for accessing the protocol information.
     *
     * Settings this property will replace the netty decoder
     * and the packet listener
     */
    var protocol: ProtocolStage
    val server: LeaflowServer

    val encryptionEnabled: Boolean

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