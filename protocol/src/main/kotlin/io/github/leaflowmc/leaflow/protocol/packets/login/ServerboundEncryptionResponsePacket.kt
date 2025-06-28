package io.github.leaflowmc.leaflow.protocol.packets.login

import io.github.leaflowmc.leaflow.protocol.listener.server.ServerLoginPacketListener
import io.github.leaflowmc.leaflow.protocol.packets.ServerPacket
import io.github.leaflowmc.leaflow.protocol.packets.type.PacketType
import io.github.leaflowmc.leaflow.protocol.packets.type.ServerLoginPackets
import kotlinx.serialization.Serializable

@Serializable
class ServerboundEncryptionResponsePacket(
    val sharedSecret: ByteArray,
    val verifyToken: ByteArray
) : ServerPacket<ServerLoginPacketListener, ServerboundEncryptionResponsePacket> {
    override fun getType(): PacketType<ServerboundEncryptionResponsePacket> {
        return ServerLoginPackets.ENCRYPTION_RESPONSE
    }

    override fun handle(listener: ServerLoginPacketListener) {
        listener.encryptionResponse(this)
    }
}