package io.github.leaflowmc.leaflow.protocol.packets.ping

import io.github.leaflowmc.leaflow.protocol.listener.server.ServerCommonPacketListener
import io.github.leaflowmc.leaflow.protocol.listener.server.ServerConfigurationPacketListener
import io.github.leaflowmc.leaflow.protocol.packets.ServerPacket
import io.github.leaflowmc.leaflow.protocol.packets.type.PacketType
import io.github.leaflowmc.leaflow.protocol.packets.type.ServerConfigurationPackets
import kotlinx.serialization.Serializable

interface ServerboundPongPacket<L : ServerCommonPacketListener, T : ServerboundPongPacket<L, T>> : ServerPacket<L, T> {
    val id: Int

    override fun handle(listener: L) {
        listener.pong(this)
    }
}

@Serializable
class ServerboundConfigurationPongPacket(override val id: Int) :
    ServerboundPongPacket<ServerConfigurationPacketListener, ServerboundConfigurationPongPacket> {
    override val type: PacketType<ServerboundConfigurationPongPacket>
        get() = ServerConfigurationPackets.PONG
}
