package io.github.leaflowmc.protocol.listener.server

import io.github.leaflowmc.protocol.packets.handshake.ServerboundHandshakePacket

interface ServerHandshakePacketListener : ServerPacketListener {
    fun handshake(packet: ServerboundHandshakePacket)
}