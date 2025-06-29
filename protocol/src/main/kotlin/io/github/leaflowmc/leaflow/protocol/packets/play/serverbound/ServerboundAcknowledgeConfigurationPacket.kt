package io.github.leaflowmc.leaflow.protocol.packets.play.serverbound

import io.github.leaflowmc.leaflow.protocol.listener.server.ServerPlayPacketListener
import io.github.leaflowmc.leaflow.protocol.packets.ServerPacket
import kotlinx.serialization.Serializable

@Serializable
class ServerboundAcknowledgeConfigurationPacket : ServerPacket<ServerPlayPacketListener> {
    override fun handle(listener: ServerPlayPacketListener) {
        listener.acknowledgeConfiguration(this)
    }

    override val terminal: Boolean
        get() = true
}