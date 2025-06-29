package io.github.leaflowmc.leaflow.protocol.packets.common

import io.github.leaflowmc.leaflow.protocol.listener.client.ClientCommonPacketListener
import io.github.leaflowmc.leaflow.protocol.packets.ClientPacket
import kotlinx.serialization.Serializable

@Serializable
class ClientboundKeepAlivePacket(val id: Long) : ClientPacket<ClientCommonPacketListener> {
    override fun handle(listener: ClientCommonPacketListener) {
        listener.keepAlive(this)
    }
}