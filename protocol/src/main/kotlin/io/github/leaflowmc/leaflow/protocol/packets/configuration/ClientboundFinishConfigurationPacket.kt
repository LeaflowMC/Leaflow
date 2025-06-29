package io.github.leaflowmc.leaflow.protocol.packets.configuration

import io.github.leaflowmc.leaflow.protocol.listener.client.ClientConfigurationPacketListener
import io.github.leaflowmc.leaflow.protocol.packets.ClientPacket
import kotlinx.serialization.Serializable

@Serializable
class ClientboundFinishConfigurationPacket : ClientPacket<ClientConfigurationPacketListener> {
    override fun handle(listener: ClientConfigurationPacketListener) {
        listener.finishConfiguration(this)
    }

    override val terminal
        get() = true
}
