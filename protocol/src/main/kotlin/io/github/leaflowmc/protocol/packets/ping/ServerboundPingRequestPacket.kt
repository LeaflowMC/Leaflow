package io.github.leaflowmc.protocol.packets.ping

import io.github.leaflowmc.protocol.listener.server.ServerPingPacketListener
import io.github.leaflowmc.protocol.listener.server.ServerPlayPacketListener
import io.github.leaflowmc.protocol.listener.server.ServerStatusPacketListener
import io.github.leaflowmc.protocol.packets.ServerPacket
import io.github.leaflowmc.protocol.packets.type.PacketType
import io.github.leaflowmc.protocol.packets.type.ServerPlayPackets
import io.github.leaflowmc.protocol.packets.type.ServerStatusPackets
import kotlinx.serialization.Serializable

interface AbstractServerboundPingRequestPacket<L : ServerPingPacketListener, T : AbstractServerboundPingRequestPacket<L, T>> : ServerPacket<L, T> {
    val timestamp: Long

    override fun handle(listener: L) {
        listener.pingRequest(this)
    }
}

@Serializable
class ServerboundStatusPingRequestPacket(
    override val timestamp: Long
) : AbstractServerboundPingRequestPacket<ServerStatusPacketListener, ServerboundStatusPingRequestPacket> {
    override fun getType(): PacketType<ServerboundStatusPingRequestPacket> {
        return ServerStatusPackets.PING_REQUEST
    }
}

@Serializable
class ServerboundPlayPingRequestPacket(
    override val timestamp: Long
) : AbstractServerboundPingRequestPacket<ServerPlayPacketListener, ServerboundPlayPingRequestPacket> {
    override fun getType(): PacketType<ServerboundPlayPingRequestPacket> {
        return ServerPlayPackets.PING_REQUEST
    }
}