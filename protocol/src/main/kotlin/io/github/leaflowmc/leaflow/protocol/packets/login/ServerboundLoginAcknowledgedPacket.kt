package io.github.leaflowmc.leaflow.protocol.packets.login

import io.github.leaflowmc.leaflow.protocol.listener.server.ServerLoginPacketListener
import io.github.leaflowmc.leaflow.protocol.packets.ServerPacket
import kotlinx.serialization.Serializable

@Serializable
class ServerboundLoginAcknowledgedPacket : ServerPacket<ServerLoginPacketListener> {
    override fun handle(listener: ServerLoginPacketListener) {
        listener.loginAcknowledged(this)
    }

    override val terminal: Boolean
        get() = true
}