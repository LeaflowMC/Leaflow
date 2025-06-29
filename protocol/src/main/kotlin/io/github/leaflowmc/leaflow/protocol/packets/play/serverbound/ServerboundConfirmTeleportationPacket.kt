package io.github.leaflowmc.leaflow.protocol.packets.play.serverbound

import io.github.leaflowmc.leaflow.common.utils.VarInt
import io.github.leaflowmc.leaflow.protocol.listener.server.ServerPlayPacketListener
import io.github.leaflowmc.leaflow.protocol.packets.ServerPacket
import kotlinx.serialization.Serializable

@Serializable
data class ServerboundConfirmTeleportationPacket(
    val id: VarInt
) : ServerPacket<ServerPlayPacketListener> {
    override fun handle(listener: ServerPlayPacketListener) {
        listener.confirmTeleportation(this)
    }
}