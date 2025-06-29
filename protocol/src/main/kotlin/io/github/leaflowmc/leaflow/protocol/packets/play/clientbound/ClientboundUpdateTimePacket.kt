package io.github.leaflowmc.leaflow.protocol.packets.play.clientbound

import io.github.leaflowmc.leaflow.protocol.listener.client.ClientPlayPacketListener
import io.github.leaflowmc.leaflow.protocol.packets.ClientPacket
import kotlinx.serialization.Serializable

/**
 * @property increase if true, client should automatically
 * advance time of day according to its ticking rate
 */
@Serializable
data class ClientboundUpdateTimePacket(
    val worldAge: Long,
    val timeOfDay: Long,
    val increase: Boolean
) : ClientPacket<ClientPlayPacketListener> {
    override fun handle(listener: ClientPlayPacketListener) {
        listener.updateTime(this)
    }
}