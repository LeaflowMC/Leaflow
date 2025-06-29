package io.github.leaflowmc.leaflow.protocol.packets.common

import io.github.leaflowmc.leaflow.protocol.listener.client.ClientCommonPacketListener
import io.github.leaflowmc.leaflow.protocol.packets.ClientPacket
import io.github.leaflowmc.leaflow.text.component.TextComponentAsNbt
import kotlinx.serialization.Serializable

@Serializable
class ClientboundDisconnectPacket(
    val reason: TextComponentAsNbt
) : ClientPacket<ClientCommonPacketListener> {
    override fun handle(listener: ClientCommonPacketListener) {
        listener.disconnect(this)
    }
}
