package io.github.leaflowmc.leaflow.protocol.packets.status

import io.github.leaflowmc.leaflow.protocol.listener.client.ClientStatusPacketListener
import io.github.leaflowmc.leaflow.protocol.packets.ClientPacket
import kotlinx.serialization.Serializable

@Serializable
data class ClientboundStatusResponsePacket(
    val jsonResponse: StatusResponseAsString
) : ClientPacket<ClientStatusPacketListener> {
    override fun handle(listener: ClientStatusPacketListener) {
        listener.statusResponse(this)
    }
}