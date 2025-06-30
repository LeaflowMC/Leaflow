package io.github.leaflowmc.leaflow.protocol.packets.common

import io.github.leaflowmc.leaflow.protocol.listener.server.ServerCommonPacketListener
import io.github.leaflowmc.leaflow.protocol.packets.ServerPacket
import io.github.leaflowmc.leaflow.serialization.annotations.NotLengthPrefixed
import kotlinx.serialization.Serializable

@Serializable
class ServerboundPluginMessagePacket(
    val id: String,
    @NotLengthPrefixed
    val data: ByteArray
) : ServerPacket<ServerCommonPacketListener> {
    override fun handle(listener: ServerCommonPacketListener) {
        listener.pluginMessage(this)
    }
}