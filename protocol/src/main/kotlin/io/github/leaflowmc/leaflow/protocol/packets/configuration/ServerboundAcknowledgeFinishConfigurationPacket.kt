package io.github.leaflowmc.leaflow.protocol.packets.configuration

import io.github.leaflowmc.leaflow.protocol.listener.server.ServerConfigurationPacketListener
import io.github.leaflowmc.leaflow.protocol.packets.ServerPacket
import kotlinx.serialization.Serializable

@Serializable
class ServerboundAcknowledgeFinishConfigurationPacket : ServerPacket<ServerConfigurationPacketListener> {
    override fun handle(listener: ServerConfigurationPacketListener) {
        listener.acknowledgeFinishConfiguration(this)
    }

    override val terminal
        get() = true
}
