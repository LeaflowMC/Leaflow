package io.github.leaflowmc.protocol.listener.client

import io.github.leaflowmc.protocol.packets.status.ClientboundStatusResponsePacket

interface ClientStatusPacketListener : ClientPacketListener {
    fun statusResponse(packet: ClientboundStatusResponsePacket)
}