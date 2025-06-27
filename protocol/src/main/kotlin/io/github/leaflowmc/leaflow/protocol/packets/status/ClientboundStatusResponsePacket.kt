package io.github.leaflowmc.leaflow.protocol.packets.status

import io.github.leaflowmc.leaflow.protocol.listener.client.ClientStatusPacketListener
import io.github.leaflowmc.leaflow.protocol.packets.ClientPacket
import io.github.leaflowmc.leaflow.protocol.packets.type.ClientStatusPackets
import io.github.leaflowmc.leaflow.protocol.packets.type.PacketType
import kotlinx.serialization.Serializable

@Serializable
data class ClientboundStatusResponsePacket(
    val jsonResponse: StatusResponseAsString
) : ClientPacket<ClientStatusPacketListener, ClientboundStatusResponsePacket> {
    override fun getType(): PacketType<ClientboundStatusResponsePacket> {
        return ClientStatusPackets.STATUS_RESPONSE
    }

    override fun handle(listener: ClientStatusPacketListener) {
        listener.statusResponse(this)
    }
}