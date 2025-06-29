package io.github.leaflowmc.leaflow.protocol.packets.configuration

import io.github.leaflowmc.leaflow.protocol.listener.server.ServerConfigurationPacketListener
import io.github.leaflowmc.leaflow.protocol.packets.ServerPacket
import io.github.leaflowmc.leaflow.protocol.types.DataPack
import kotlinx.serialization.Serializable

@Serializable
class ServerboundKnownPacksPacket(
    val packs: List<DataPack>
) : ServerPacket<ServerConfigurationPacketListener> {
    override fun handle(listener: ServerConfigurationPacketListener) {
        listener.knownPacks(this)
    }
}