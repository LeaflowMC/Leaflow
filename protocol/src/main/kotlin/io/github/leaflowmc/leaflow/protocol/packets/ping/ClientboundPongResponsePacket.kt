package io.github.leaflowmc.leaflow.protocol.packets.ping

import io.github.leaflowmc.leaflow.protocol.listener.client.ClientPingPacketListener
import io.github.leaflowmc.leaflow.protocol.listener.client.ClientStatusPacketListener
import io.github.leaflowmc.leaflow.protocol.packets.ClientPacket
import io.github.leaflowmc.leaflow.protocol.packets.type.ClientStatusPackets
import io.github.leaflowmc.leaflow.protocol.packets.type.PacketType
import kotlinx.serialization.Serializable

interface ClientboundPongResponsePacket<L : ClientPingPacketListener, T : ClientboundPongResponsePacket<L, T>> : ClientPacket<L, T> {
    val timestamp: Long

    override fun handle(listener: L) {
        listener.pongResponse(this)
    }
}

@Serializable
class ClientboundStatusPongResponsePacket(override val timestamp: Long) : ClientboundPongResponsePacket<ClientStatusPacketListener, ClientboundStatusPongResponsePacket> {
    override fun getType(): PacketType<ClientboundStatusPongResponsePacket> {
        return ClientStatusPackets.PONG_RESPONSE
    }
}