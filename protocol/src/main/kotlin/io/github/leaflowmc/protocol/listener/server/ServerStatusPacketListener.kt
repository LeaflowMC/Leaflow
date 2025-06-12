package io.github.leaflowmc.protocol.listener.server

import io.github.leaflowmc.protocol.packets.status.ServerboundStatusRequestPacket

interface ServerStatusPacketListener : ServerPacketListener, ServerPingPacketListener {
    fun statusRequest(packet: ServerboundStatusRequestPacket)
}