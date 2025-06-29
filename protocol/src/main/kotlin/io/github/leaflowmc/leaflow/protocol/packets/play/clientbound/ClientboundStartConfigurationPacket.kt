package io.github.leaflowmc.leaflow.protocol.packets.play.clientbound

import io.github.leaflowmc.leaflow.protocol.listener.client.ClientPlayPacketListener
import io.github.leaflowmc.leaflow.protocol.packets.ClientPacket
import kotlinx.serialization.Serializable

@Serializable
class ClientboundStartConfigurationPacket : ClientPacket<ClientPlayPacketListener> {
    override fun handle(listener: ClientPlayPacketListener) {
        listener.startConfiguration(this)
    }

    override val terminal: Boolean
        get() = true
}