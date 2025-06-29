package io.github.leaflowmc.leaflow.protocol.packets.handshake

import io.github.leaflowmc.leaflow.common.utils.VarInt
import io.github.leaflowmc.leaflow.protocol.listener.server.ServerHandshakePacketListener
import io.github.leaflowmc.leaflow.protocol.packets.ServerPacket
import kotlinx.serialization.Serializable

@Serializable
data class ServerboundHandshakePacket(
    val protocolVersion: VarInt,
    val address: String,
    val port: UShort,
    val next: Intent
) : ServerPacket<ServerHandshakePacketListener> {
    override fun handle(listener: ServerHandshakePacketListener) {
        listener.handshake(this)
    }

    override val terminal: Boolean
        get() = true

    enum class Intent {
        NONE,
        STATUS,
        LOGIN,
        TRANSFER;
    }
}