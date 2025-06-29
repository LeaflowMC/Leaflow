package io.github.leaflowmc.leaflow.protocol.packets.login

import io.github.leaflowmc.leaflow.protocol.listener.client.ClientLoginPacketListener
import io.github.leaflowmc.leaflow.protocol.packets.ClientPacket
import kotlinx.serialization.Serializable

@Serializable
class ClientboundEncryptionRequestPacket(
    val serverId: String,
    val publicKey: ByteArray,
    val verifyToken: ByteArray,
    val authEnabled: Boolean
) : ClientPacket<ClientLoginPacketListener> {
    override fun handle(listener: ClientLoginPacketListener) {
        listener.encryptionRequest(this)
    }
}