package io.github.leaflowmc.leaflow.protocol.packets.common

import io.github.leaflowmc.leaflow.protocol.listener.client.ClientCommonPacketListener
import io.github.leaflowmc.leaflow.protocol.listener.client.ClientConfigurationPacketListener
import io.github.leaflowmc.leaflow.protocol.packets.ClientPacket
import io.github.leaflowmc.leaflow.protocol.packets.type.ClientConfigurationPackets
import io.github.leaflowmc.leaflow.protocol.packets.type.PacketType
import kotlinx.serialization.Serializable

interface ClientboundKeepAlivePacket<L : ClientCommonPacketListener, T : ClientboundKeepAlivePacket<L, T>> : ClientPacket<L, T> {
    val id: Long

    override fun handle(listener: L) {
        listener.keepAlive(this)
    }
}

@Serializable
class ClientboundConfigurationKeepAlivePacket(override val id: Long) :
    ClientboundKeepAlivePacket<ClientConfigurationPacketListener, ClientboundConfigurationKeepAlivePacket> {

    override val type: PacketType<ClientboundConfigurationKeepAlivePacket>
        get() = ClientConfigurationPackets.KEEP_ALIVE
}
