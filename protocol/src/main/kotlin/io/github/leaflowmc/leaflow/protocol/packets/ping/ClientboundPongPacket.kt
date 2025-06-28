package io.github.leaflowmc.leaflow.protocol.packets.ping

import io.github.leaflowmc.leaflow.protocol.listener.client.ClientPingPacketListener
import io.github.leaflowmc.leaflow.protocol.listener.client.ClientStatusPacketListener
import io.github.leaflowmc.leaflow.protocol.packets.ClientPacket
import io.github.leaflowmc.leaflow.protocol.packets.type.ClientStatusPackets
import io.github.leaflowmc.leaflow.protocol.packets.type.PacketType
import kotlinx.serialization.Serializable

interface ClientboundPongPacket<L : ClientPingPacketListener, T : ClientboundPongPacket<L, T>> :
    ClientPacket<L, T> {
    val timestamp: Long

    override fun handle(listener: L) {
        listener.pongResponse(this)
    }
}

@Serializable
class ClientboundStatusPongPacket(override val timestamp: Long) :
    ClientboundPongPacket<ClientStatusPacketListener, ClientboundStatusPongPacket> {

    override val type: PacketType<ClientboundStatusPongPacket>
        get() = ClientStatusPackets.PONG_RESPONSE
}
