package io.github.leaflowmc.leaflow.protocol.listener.server

import io.github.leaflowmc.leaflow.protocol.packets.status.ServerboundStatusRequestPacket

interface ServerStatusPacketListener : ServerPacketListener, ServerPingPacketListener {
    fun statusRequest(packet: ServerboundStatusRequestPacket)
}