package io.github.leaflowmc.leaflow.protocol.packets.play.clientbound

import io.github.leaflowmc.leaflow.protocol.listener.client.ClientPlayPacketListener
import io.github.leaflowmc.leaflow.protocol.packets.ClientPacket
import io.github.leaflowmc.leaflow.text.component.TextComponentAsNbt
import kotlinx.serialization.Serializable

@Serializable
data class ClientboundSetTitleTextPacket(
    val title: TextComponentAsNbt
) : ClientPacket<ClientPlayPacketListener> {
    override fun handle(listener: ClientPlayPacketListener) {
        listener.setTitleText(this)
    }
}