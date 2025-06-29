package io.github.leaflowmc.leaflow.protocol.listener.client

import io.github.leaflowmc.leaflow.protocol.packets.ping.ClientboundPongPacket

interface ClientPingPacketListener : ClientPacketListener {
    fun pongResponse(packet: ClientboundPongPacket)
}