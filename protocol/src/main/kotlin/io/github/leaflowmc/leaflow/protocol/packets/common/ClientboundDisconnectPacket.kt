package io.github.leaflowmc.leaflow.protocol.packets.common

import io.github.leaflowmc.leaflow.protocol.listener.client.ClientCommonPacketListener
import io.github.leaflowmc.leaflow.protocol.packets.ClientPacket
import io.github.leaflowmc.leaflow.serialization.annotations.AsNbt
import io.github.leaflowmc.leaflow.text.component.TextComponent
import kotlinx.serialization.Serializable

@Serializable
class ClientboundDisconnectPacket(
    @AsNbt
    val reason: TextComponent
) : ClientPacket<ClientCommonPacketListener> {
    override fun handle(listener: ClientCommonPacketListener) {
        listener.disconnect(this)
    }
}
