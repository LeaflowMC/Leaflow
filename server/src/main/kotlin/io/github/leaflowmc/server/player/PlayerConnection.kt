package io.github.leaflowmc.server.player

import io.github.leaflowmc.protocol.ProtocolStage
import io.github.leaflowmc.protocol.packets.ClientPacket
import io.github.leaflowmc.server.LeaflowServer
import io.netty.channel.ChannelInboundHandler

interface PlayerConnection : ChannelInboundHandler {
    /**
     * Property for accessing the protocol information.
     *
     * Settings this property will replace the netty decoder
     * and the packet listener
     */
    var protocol: ProtocolStage
    val server: LeaflowServer

    /**
     * Sends the packet to the player.
     */
    fun sendPacket(packet: ClientPacket<*, *>)
}