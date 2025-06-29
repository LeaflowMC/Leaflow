package io.github.leaflowmc.leaflow.protocol.listener.client

import io.github.leaflowmc.leaflow.protocol.packets.login.ClientboundEncryptionRequestPacket
import io.github.leaflowmc.leaflow.protocol.packets.login.ClientboundLoginDisconnectPacket
import io.github.leaflowmc.leaflow.protocol.packets.login.ClientboundLoginSuccessPacket

interface ClientLoginPacketListener : ClientPacketListener {
    fun encryptionRequest(packet: ClientboundEncryptionRequestPacket)
    fun loginSuccess(packet: ClientboundLoginSuccessPacket)
    fun disconnect(packet: ClientboundLoginDisconnectPacket)
}