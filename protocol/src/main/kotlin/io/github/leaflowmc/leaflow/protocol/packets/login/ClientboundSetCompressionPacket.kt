package io.github.leaflowmc.leaflow.protocol.packets.login

import io.github.leaflowmc.leaflow.common.utils.VarInt
import io.github.leaflowmc.leaflow.protocol.listener.client.ClientLoginPacketListener
import io.github.leaflowmc.leaflow.protocol.packets.ClientPacket
import kotlinx.serialization.Serializable

@Serializable
class ClientboundSetCompressionPacket(
    val threshold: VarInt
) : ClientPacket<ClientLoginPacketListener> {
    override fun handle(listener: ClientLoginPacketListener) {
        listener.setCompression(this)
    }
}