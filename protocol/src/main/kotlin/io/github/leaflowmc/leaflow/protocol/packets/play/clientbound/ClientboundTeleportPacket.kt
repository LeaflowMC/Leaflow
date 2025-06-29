package io.github.leaflowmc.leaflow.protocol.packets.play.clientbound

import io.github.leaflowmc.leaflow.common.utils.VarInt
import io.github.leaflowmc.leaflow.common.utils.checkBits
import io.github.leaflowmc.leaflow.math.Vector3D
import io.github.leaflowmc.leaflow.protocol.listener.client.ClientPlayPacketListener
import io.github.leaflowmc.leaflow.protocol.packets.ClientPacket
import io.github.leaflowmc.leaflow.protocol.types.flags.TeleportFlags
import kotlinx.serialization.Serializable

@Serializable
data class ClientboundTeleportPacket(
    val id: VarInt,
    val position: Vector3D,
    val velocity: Vector3D,
    val yaw: Float,
    val pitch: Float,
    val flags: TeleportFlags
) : ClientPacket<ClientPlayPacketListener> {
    override fun handle(listener: ClientPlayPacketListener) {
        listener.teleport(this)
    }
}