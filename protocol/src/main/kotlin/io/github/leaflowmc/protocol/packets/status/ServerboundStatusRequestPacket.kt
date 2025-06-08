package io.github.leaflowmc.protocol.packets.status

import io.github.leaflowmc.protocol.packets.ServerPacket
import io.github.leaflowmc.protocol.ServerPacketListener
import kotlinx.serialization.Serializable

@Serializable
class ServerboundStatusRequestPacket : ServerPacket {
    override suspend fun handle(listener: ServerPacketListener) {
        listener.statusRequest(this)
    }
}