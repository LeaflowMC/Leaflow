package io.github.leaflowmc.leaflow.protocol.packets.common

import io.github.leaflowmc.leaflow.protocol.listener.client.ClientCommonPacketListener
import io.github.leaflowmc.leaflow.protocol.packets.ClientPacket
import io.github.leaflowmc.leaflow.serialization.annotations.NotLengthPrefixed
import kotlinx.serialization.Serializable

@Serializable
class ClientboundPluginMessagePacket(
    val id: String,
    @NotLengthPrefixed
    val data: ByteArray
) : ClientPacket<ClientCommonPacketListener> {
    override fun handle(listener: ClientCommonPacketListener) {
        listener.pluginMessage(this)
    }
}