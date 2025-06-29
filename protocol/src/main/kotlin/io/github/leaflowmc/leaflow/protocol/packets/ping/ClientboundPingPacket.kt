package io.github.leaflowmc.leaflow.protocol.packets.ping

import io.github.leaflowmc.leaflow.protocol.listener.client.ClientCommonPacketListener
import io.github.leaflowmc.leaflow.protocol.packets.ClientPacket
import kotlinx.serialization.Serializable

@Serializable
class ClientboundPingPacket(val id: Int) : ClientPacket<ClientCommonPacketListener> {
    override fun handle(listener: ClientCommonPacketListener) {
        listener.ping(this)
    }
}