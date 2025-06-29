package io.github.leaflowmc.leaflow.protocol.packets.ping

import io.github.leaflowmc.leaflow.protocol.listener.server.ServerCommonPacketListener
import io.github.leaflowmc.leaflow.protocol.packets.ServerPacket
import kotlinx.serialization.Serializable

@Serializable
class ServerboundPongPacket(val id: Int) : ServerPacket<ServerCommonPacketListener> {
    override fun handle(listener: ServerCommonPacketListener) {
        listener.pong(this)
    }
}