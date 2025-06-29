package io.github.leaflowmc.leaflow.protocol.packets.play.clientbound

import io.github.leaflowmc.leaflow.protocol.listener.client.ClientPlayPacketListener
import io.github.leaflowmc.leaflow.protocol.packets.ClientPacket
import io.github.leaflowmc.leaflow.protocol.types.flags.PlayerAbilities
import kotlinx.serialization.Serializable

@Serializable
data class ClientboundAbilitiesPacket(
    val abilities: PlayerAbilities,
    val flyingSpeed: Float,
    val fovModifier: Float
) : ClientPacket<ClientPlayPacketListener> {

    override fun handle(listener: ClientPlayPacketListener) {
        listener.abilities(this)
    }
}