package io.github.leaflowmc.leaflow.protocol.packets.status

import io.github.leaflowmc.leaflow.protocol.listener.server.ServerStatusPacketListener
import io.github.leaflowmc.leaflow.protocol.packets.ServerPacket
import kotlinx.serialization.Serializable

@Serializable
class ServerboundStatusRequestPacket : ServerPacket<ServerStatusPacketListener> {
    override fun handle(listener: ServerStatusPacketListener) {
        listener.statusRequest(this)
    }
}