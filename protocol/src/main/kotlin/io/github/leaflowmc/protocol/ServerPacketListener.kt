package io.github.leaflowmc.protocol

import io.github.leaflowmc.protocol.packets.handshake.ServerboundHandshakePacket
import io.github.leaflowmc.protocol.packets.status.ServerboundStatusRequestPacket

interface ServerPacketListener {
    suspend fun handshake(packet: ServerboundHandshakePacket)
    suspend fun statusRequest(packet: ServerboundStatusRequestPacket)
}