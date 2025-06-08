package io.github.leaflowmc.server.player

import io.github.leaflowmc.protocol.ProtocolStage
import io.github.leaflowmc.protocol.listener.server.ServerPacketListener
import io.github.leaflowmc.protocol.packets.ClientPacket
import io.github.leaflowmc.protocol.packets.ServerPacket
import io.github.leaflowmc.protocol.packets.type.ProtocolInfo
import io.github.leaflowmc.protocol.packets.type.getServerProtocolFor
import io.github.leaflowmc.server.LeaflowServer
import io.netty.channel.ChannelInboundHandler

interface PlayerConnection : ChannelInboundHandler {
    /**
     * Protocol info helps the decoder decode packets
     */
    val protocol: ProtocolStage
    val server: LeaflowServer

    /**
     * Sends the packet to the player.
     */
    fun sendPacket(packet: ClientPacket<*, *>)

    /**
     * Change protocol
     *
     * Replaces this handler in netty with a new one (theoretically a thread-safe operation)
     */
    fun changeProtocol(protocol: ProtocolStage)
}