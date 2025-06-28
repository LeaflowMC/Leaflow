package io.github.leaflowmc.leaflow.protocol.packets.configuration

import io.github.leaflowmc.leaflow.protocol.listener.client.ClientConfigurationPacketListener
import io.github.leaflowmc.leaflow.protocol.packets.ClientPacket
import io.github.leaflowmc.leaflow.protocol.packets.type.ClientConfigurationPackets
import io.github.leaflowmc.leaflow.protocol.packets.type.PacketType
import io.github.leaflowmc.leaflow.protocol.types.DataPack
import kotlinx.serialization.Serializable

@Serializable
class ClientboundKnownPacksPacket(val packs: List<DataPack>) : ClientPacket<ClientConfigurationPacketListener, ClientboundKnownPacksPacket> {
    override val type: PacketType<ClientboundKnownPacksPacket>
        get() = ClientConfigurationPackets.KNOWN_PACKS

    override fun handle(listener: ClientConfigurationPacketListener) {
        listener.knownPacks(this)
    }
}