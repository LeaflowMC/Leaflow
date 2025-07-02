package io.github.leaflowmc.leaflow.protocol.packets.play.clientbound

import io.github.leaflowmc.leaflow.protocol.listener.client.ClientPlayPacketListener
import io.github.leaflowmc.leaflow.protocol.packets.ClientPacket
import io.github.leaflowmc.leaflow.serialization.annotations.AsNbt
import io.github.leaflowmc.leaflow.text.component.TextComponent
import kotlinx.serialization.Serializable

@Serializable
data class ClientboundSetTitleTextPacket(
    @AsNbt
    val title: TextComponent
) : ClientPacket<ClientPlayPacketListener> {
    override fun handle(listener: ClientPlayPacketListener) {
        listener.setTitleText(this)
    }
}