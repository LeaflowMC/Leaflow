package io.github.leaflowmc.leaflow.protocol.packets.configuration

import io.github.leaflowmc.leaflow.protocol.listener.client.ClientConfigurationPacketListener
import io.github.leaflowmc.leaflow.protocol.packets.ClientPacket
import io.github.leaflowmc.leaflow.protocol.types.DataPack
import kotlinx.serialization.Serializable

@Serializable
class ClientboundKnownPacksPacket(val packs: List<DataPack>) : ClientPacket<ClientConfigurationPacketListener> {
    override fun handle(listener: ClientConfigurationPacketListener) {
        listener.knownPacks(this)
    }
}