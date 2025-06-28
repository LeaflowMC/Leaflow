package io.github.leaflowmc.leaflow.protocol.packets.configuration

import io.github.leaflowmc.leaflow.protocol.listener.server.ServerConfigurationPacketListener
import io.github.leaflowmc.leaflow.protocol.packets.ServerPacket
import io.github.leaflowmc.leaflow.protocol.packets.type.PacketType
import io.github.leaflowmc.leaflow.protocol.packets.type.ServerConfigurationPackets
import io.github.leaflowmc.leaflow.protocol.types.DataPack
import kotlinx.serialization.Serializable

@Serializable
class ServerboundKnownPacksPacket(
    val packs: List<DataPack>
) : ServerPacket<ServerConfigurationPacketListener, ServerboundKnownPacksPacket> {
    override val type: PacketType<ServerboundKnownPacksPacket>
        get() = ServerConfigurationPackets.KNOWN_PACKS

    override fun handle(listener: ServerConfigurationPacketListener) {
        listener.knownPacks(this)
    }
}