package io.github.leaflowmc.leaflow.protocol.listener.server

import io.github.leaflowmc.leaflow.protocol.packets.handshake.ServerboundHandshakePacket

interface ServerHandshakePacketListener : ServerPacketListener {
    fun handshake(packet: ServerboundHandshakePacket)
}