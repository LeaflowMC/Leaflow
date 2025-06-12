package io.github.leaflowmc.protocol.listener.client

import io.github.leaflowmc.protocol.packets.ping.ClientboundPongResponsePacket

interface ClientPingPacketListener : ClientPacketListener {
    fun pongResponse(packet: ClientboundPongResponsePacket<*, *>)
}