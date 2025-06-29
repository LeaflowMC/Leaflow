package io.github.leaflowmc.leaflow.protocol.packets.login

import io.github.leaflowmc.leaflow.protocol.listener.client.ClientLoginPacketListener
import io.github.leaflowmc.leaflow.protocol.packets.ClientPacket
import io.github.leaflowmc.leaflow.text.component.TextComponentAsString
import kotlinx.serialization.Serializable

@Serializable
data class ClientboundLoginDisconnectPacket(
    val reason: TextComponentAsString
) : ClientPacket<ClientLoginPacketListener> {
    override fun handle(listener: ClientLoginPacketListener) {
        listener.disconnect(this)
    }
}