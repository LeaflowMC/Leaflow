package io.github.leaflowmc.protocol.packets

import io.github.leaflowmc.protocol.packets.handshake.ServerboundHandshakePacket

interface ServerPacketListener {
    suspend fun handshake(packet: ServerboundHandshakePacket)
}