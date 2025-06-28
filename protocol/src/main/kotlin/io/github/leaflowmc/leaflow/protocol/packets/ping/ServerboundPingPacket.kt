package io.github.leaflowmc.leaflow.protocol.packets.ping

import io.github.leaflowmc.leaflow.protocol.listener.server.ServerPingPacketListener
import io.github.leaflowmc.leaflow.protocol.listener.server.ServerPlayPacketListener
import io.github.leaflowmc.leaflow.protocol.listener.server.ServerStatusPacketListener
import io.github.leaflowmc.leaflow.protocol.packets.ServerPacket
import io.github.leaflowmc.leaflow.protocol.packets.type.PacketType
import io.github.leaflowmc.leaflow.protocol.packets.type.ServerPlayPackets
import io.github.leaflowmc.leaflow.protocol.packets.type.ServerStatusPackets
import kotlinx.serialization.Serializable

interface ServerboundPingPacket<L : ServerPingPacketListener, T : ServerboundPingPacket<L, T>> : ServerPacket<L, T> {
    val timestamp: Long

    override fun handle(listener: L) {
        listener.pingRequest(this)
    }
}

@Serializable
class ServerboundStatusPingPacket(
    override val timestamp: Long
) : ServerboundPingPacket<ServerStatusPacketListener, ServerboundStatusPingPacket> {
    override val type: PacketType<ServerboundStatusPingPacket>
        get() = ServerStatusPackets.PING_REQUEST
}

@Serializable
class ServerboundPlayPingPacket(
    override val timestamp: Long
) : ServerboundPingPacket<ServerPlayPacketListener, ServerboundPlayPingPacket> {
    override val type: PacketType<ServerboundPlayPingPacket>
        get() = ServerPlayPackets.PING_REQUEST
}