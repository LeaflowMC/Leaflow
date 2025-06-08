package io.github.leaflowmc.protocol.listener.server

import io.github.leaflowmc.protocol.packets.status.ServerboundStatusRequestPacket

interface ServerStatusPacketListener : ServerPacketListener {
    fun statusRequest(packet: ServerboundStatusRequestPacket)
}