package io.github.leaflowmc.leaflow.protocol.packets.status

import io.github.leaflowmc.leaflow.protocol.packets.ServerPacket
import io.github.leaflowmc.leaflow.protocol.listener.server.ServerStatusPacketListener
import io.github.leaflowmc.leaflow.protocol.packets.type.PacketType
import io.github.leaflowmc.leaflow.protocol.packets.type.ServerStatusPackets
import kotlinx.serialization.Serializable

@Serializable
class ServerboundStatusRequestPacket : ServerPacket<ServerStatusPacketListener, ServerboundStatusRequestPacket> {
    override fun getType(): PacketType<ServerboundStatusRequestPacket> {
        return ServerStatusPackets.STATUS_REQUEST
    }

    override fun handle(listener: ServerStatusPacketListener) {
        listener.statusRequest(this)
    }
}