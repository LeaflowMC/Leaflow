package io.github.leaflowmc.leaflow.protocol.packets.play.serverbound

import io.github.leaflowmc.leaflow.math.Vector3D
import io.github.leaflowmc.leaflow.protocol.listener.server.ServerPlayPacketListener
import io.github.leaflowmc.leaflow.protocol.packets.ServerPacket
import io.github.leaflowmc.leaflow.protocol.types.flags.MovementFlags
import kotlinx.serialization.Serializable

@Serializable
data class ServerboundSetPositionAndRotationPacket(
    val position: Vector3D,
    val yaw: Float,
    val pitch: Float,
    val flags: MovementFlags
) : ServerPacket<ServerPlayPacketListener> {
    override fun handle(listener: ServerPlayPacketListener) {
        listener.setPositionAndRotation(this)
    }
}