package io.github.leaflowmc.leaflow.protocol.packets.common

import io.github.leaflowmc.leaflow.protocol.listener.server.ServerCommonPacketListener
import io.github.leaflowmc.leaflow.protocol.listener.server.ServerConfigurationPacketListener
import io.github.leaflowmc.leaflow.protocol.packets.ServerPacket
import io.github.leaflowmc.leaflow.protocol.packets.type.PacketType
import io.github.leaflowmc.leaflow.protocol.packets.type.ServerConfigurationPackets
import kotlinx.serialization.Serializable

interface ServerboundKeepAlivePacket<L : ServerCommonPacketListener, T : ServerboundKeepAlivePacket<L, T>> :
    ServerPacket<L, T> {
    val id: Long

    override fun handle(listener: L) {
        listener.keepAlive(this)
    }
}

@Serializable
class ServerboundConfigurationKeepAlivePacket(override val id: Long) :
    ServerboundKeepAlivePacket<ServerConfigurationPacketListener, ServerboundConfigurationKeepAlivePacket> {

    override val type: PacketType<ServerboundConfigurationKeepAlivePacket>
        get() = ServerConfigurationPackets.KEEP_ALIVE
}