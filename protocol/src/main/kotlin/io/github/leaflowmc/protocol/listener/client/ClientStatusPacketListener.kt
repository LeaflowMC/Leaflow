package io.github.leaflowmc.protocol.listener.client

import io.github.leaflowmc.protocol.packets.status.ClientboundStatusResponsePacket

interface ClientStatusPacketListener : ClientPacketListener, ClientPingPacketListener {
    fun statusResponse(packet: ClientboundStatusResponsePacket)
}