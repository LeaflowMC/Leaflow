package io.github.leaflowmc.leaflow.protocol.listener.client

import io.github.leaflowmc.leaflow.protocol.packets.ping.ClientboundPongResponsePacket

interface ClientPingPacketListener : ClientPacketListener {
    fun pongResponse(packet: ClientboundPongResponsePacket<*, *>)
}