package io.github.leaflowmc.leaflow.protocol.listener.client

import io.github.leaflowmc.leaflow.protocol.packets.status.ClientboundStatusResponsePacket

interface ClientStatusPacketListener : ClientPacketListener, ClientPingPacketListener {
    fun statusResponse(packet: ClientboundStatusResponsePacket)
}