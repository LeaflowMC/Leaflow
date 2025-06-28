package io.github.leaflowmc.leaflow.protocol.packets.ping

import io.github.leaflowmc.leaflow.protocol.listener.client.ClientCommonPacketListener
import io.github.leaflowmc.leaflow.protocol.listener.client.ClientConfigurationPacketListener
import io.github.leaflowmc.leaflow.protocol.listener.client.ClientPlayPacketListener
import io.github.leaflowmc.leaflow.protocol.packets.ClientPacket
import io.github.leaflowmc.leaflow.protocol.packets.type.ClientConfigurationPackets
import io.github.leaflowmc.leaflow.protocol.packets.type.ClientPlayPackets
import io.github.leaflowmc.leaflow.protocol.packets.type.PacketType
import kotlinx.serialization.Serializable

interface ClientboundPingPacket<L : ClientCommonPacketListener, T : ClientboundPingPacket<L, T>> : ClientPacket<L, T> {
    val id: Int

    override fun handle(listener: L) {
        listener.ping(this)
    }
}

@Serializable
class ClientboundConfigurationPingPacket(override val id: Int) :
    ClientboundPingPacket<ClientConfigurationPacketListener, ClientboundConfigurationPingPacket> {

    override val type: PacketType<ClientboundConfigurationPingPacket>
        get() = ClientConfigurationPackets.PING
}

@Serializable
class ClientboundPlayPingPacket(override val id: Int) : ClientboundPingPacket<ClientPlayPacketListener, ClientboundPlayPingPacket> {
    override val type: PacketType<ClientboundPlayPingPacket>
        get() = ClientPlayPackets.PING
}
