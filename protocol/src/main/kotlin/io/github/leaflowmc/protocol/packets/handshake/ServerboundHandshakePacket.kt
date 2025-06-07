package io.github.leaflowmc.protocol.packets.handshake

import io.github.leaflowmc.common.utils.VarInt
import io.github.leaflowmc.protocol.packets.ProtocolStage
import io.github.leaflowmc.protocol.packets.ServerPacket
import io.github.leaflowmc.protocol.packets.ServerPacketListener
import kotlinx.serialization.Serializable

@Serializable
data class ServerboundHandshakePacket(
    val protocolVersion: VarInt,
    val address: String,
    val port: UShort,
    val next: ProtocolStage
) : ServerPacket {
    override suspend fun handle(listener: ServerPacketListener) {
        listener.handshake(this)
    }
}