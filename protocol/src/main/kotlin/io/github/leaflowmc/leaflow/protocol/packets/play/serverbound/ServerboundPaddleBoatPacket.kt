package io.github.leaflowmc.leaflow.protocol.packets.play.serverbound

import io.github.leaflowmc.leaflow.protocol.listener.server.ServerPlayPacketListener
import io.github.leaflowmc.leaflow.protocol.packets.ServerPacket
import kotlinx.serialization.Serializable

@Serializable
data class ServerboundPaddleBoatPacket(
    val leftTurning: Boolean,
    val rightTurning: Boolean
) : ServerPacket<ServerPlayPacketListener> {
    override fun handle(listener: ServerPlayPacketListener) {
        listener.paddleBoat(this)
    }
}