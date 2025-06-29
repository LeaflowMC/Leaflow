package io.github.leaflowmc.leaflow.protocol.packets.play.clientbound

import io.github.leaflowmc.leaflow.protocol.listener.client.ClientPlayPacketListener
import io.github.leaflowmc.leaflow.protocol.packets.ClientPacket
import io.github.leaflowmc.leaflow.protocol.types.Position
import kotlinx.serialization.Serializable

@Serializable
data class ClientboundSetDefaultSpawnPositionPacket(
    val position: Position,
    val angle: Float
) : ClientPacket<ClientPlayPacketListener> {
    override fun handle(listener: ClientPlayPacketListener) {
        listener.setDefaultSpawnPosition(this)
    }
}