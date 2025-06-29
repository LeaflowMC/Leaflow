package io.github.leaflowmc.leaflow.protocol.packets.play.serverbound

import io.github.leaflowmc.leaflow.protocol.listener.server.ServerPlayPacketListener
import io.github.leaflowmc.leaflow.protocol.packets.ServerPacket
import io.github.leaflowmc.leaflow.protocol.types.flags.MovementFlags
import kotlinx.serialization.Serializable

@Serializable
data class ServerboundSetMovementFlagsPacket(
    val flags: MovementFlags
) : ServerPacket<ServerPlayPacketListener> {
    override fun handle(listener: ServerPlayPacketListener) {
        listener.setMovementFlags(this)
    }
}