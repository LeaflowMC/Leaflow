package io.github.leaflowmc.protocol.packets.handshake

import io.github.leaflowmc.common.utils.VarInt
import io.github.leaflowmc.protocol.ProtocolStage
import io.github.leaflowmc.protocol.listener.server.ServerHandshakePacketListener
import io.github.leaflowmc.protocol.packets.ServerPacket
import io.github.leaflowmc.protocol.packets.type.PacketType
import io.github.leaflowmc.protocol.packets.type.ServerHandshakePackets
import kotlinx.serialization.Serializable

@Serializable
data class ServerboundHandshakePacket(
    val protocolVersion: VarInt,
    val address: String,
    val port: UShort,
    val next: Intent
) : ServerPacket<ServerHandshakePacketListener, ServerboundHandshakePacket> {
    override fun handle(listener: ServerHandshakePacketListener) {
        listener.handshake(this)
    }

    override fun getType(): PacketType<ServerboundHandshakePacket> {
        return ServerHandshakePackets.HANDSHAKE
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