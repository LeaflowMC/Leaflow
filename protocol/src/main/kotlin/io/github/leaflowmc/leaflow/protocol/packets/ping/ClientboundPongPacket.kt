package io.github.leaflowmc.leaflow.protocol.packets.ping

import io.github.leaflowmc.leaflow.protocol.listener.client.ClientPingPacketListener
import io.github.leaflowmc.leaflow.protocol.packets.ClientPacket
import kotlinx.serialization.Serializable

@Serializable
class ClientboundPongPacket(val timestamp: Long) : ClientPacket<ClientPingPacketListener> {
    override fun handle(listener: ClientPingPacketListener) {
        listener.pongResponse(this)
    }
}