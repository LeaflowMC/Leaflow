package io.github.leaflowmc.leaflow.protocol.packets.ping

import io.github.leaflowmc.leaflow.protocol.listener.server.ServerPingPacketListener
import io.github.leaflowmc.leaflow.protocol.packets.ServerPacket
import kotlinx.serialization.Serializable

@Serializable
class ServerboundPingPacket(val timestamp: Long) : ServerPacket<ServerPingPacketListener> {
    override fun handle(listener: ServerPingPacketListener) {
        listener.pingRequest(this)
    }
}