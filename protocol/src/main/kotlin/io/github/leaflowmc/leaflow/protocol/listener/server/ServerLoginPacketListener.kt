package io.github.leaflowmc.leaflow.protocol.listener.server

import io.github.leaflowmc.leaflow.protocol.packets.login.ServerboundEncryptionResponsePacket
import io.github.leaflowmc.leaflow.protocol.packets.login.ServerboundLoginAcknowledgedPacket
import io.github.leaflowmc.leaflow.protocol.packets.login.ServerboundLoginStartPacket

interface ServerLoginPacketListener : ServerPacketListener {
    fun loginStart(packet: ServerboundLoginStartPacket)
    fun encryptionResponse(packet: ServerboundEncryptionResponsePacket)
    fun loginAcknowledged(packet: ServerboundLoginAcknowledgedPacket)
}