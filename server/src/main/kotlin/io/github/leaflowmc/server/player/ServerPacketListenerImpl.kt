package io.github.leaflowmc.server.player

import io.github.leaflowmc.protocol.ProtocolStage
import io.github.leaflowmc.protocol.ServerPacketListener
import io.github.leaflowmc.protocol.packets.handshake.ServerboundHandshakePacket
import io.github.leaflowmc.protocol.packets.status.ServerboundStatusRequestPacket

open class ServerPacketListenerImpl(val player: Player) : ServerPacketListener {
    override suspend fun handshake(packet: ServerboundHandshakePacket) {
        when (packet.next) {
            ProtocolStage.STATUS,
            ProtocolStage.LOGIN -> player.protocolStage = packet.next

            else -> throw IllegalStateException("Player tries to progress in a forbidden protocol stage")
        }
    }

    override suspend fun statusRequest(packet: ServerboundStatusRequestPacket) {
    }
}