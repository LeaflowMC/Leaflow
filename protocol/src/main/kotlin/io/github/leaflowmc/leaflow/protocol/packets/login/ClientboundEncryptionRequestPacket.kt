package io.github.leaflowmc.leaflow.protocol.packets.login

import io.github.leaflowmc.leaflow.protocol.listener.client.ClientLoginPacketListener
import io.github.leaflowmc.leaflow.protocol.packets.ClientPacket
import io.github.leaflowmc.leaflow.protocol.packets.type.ClientLoginPackets
import io.github.leaflowmc.leaflow.protocol.packets.type.PacketType
import kotlinx.serialization.Serializable

@Serializable
class ClientboundEncryptionRequestPacket(
    val serverId: String,
    val publicKey: ByteArray,
    val verifyToken: ByteArray,
    val authEnabled: Boolean
) : ClientPacket<ClientLoginPacketListener, ClientboundEncryptionRequestPacket> {
    override val type: PacketType<ClientboundEncryptionRequestPacket>
        get() = ClientLoginPackets.ENCRYPTION_REQUEST

    override fun handle(listener: ClientLoginPacketListener) {
        listener.encryptionRequest(this)
    }
}