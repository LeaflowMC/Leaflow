package io.github.leaflowmc.leaflow.protocol.packets.common

import io.github.leaflowmc.leaflow.protocol.listener.server.ServerCommonPacketListener
import io.github.leaflowmc.leaflow.protocol.packets.ServerPacket
import kotlinx.serialization.Serializable

@Serializable
class ServerboundKeepAlivePacket(val id: Long) : ServerPacket<ServerCommonPacketListener> {
    override fun handle(listener: ServerCommonPacketListener) {
        listener.keepAlive(this)
    }
}