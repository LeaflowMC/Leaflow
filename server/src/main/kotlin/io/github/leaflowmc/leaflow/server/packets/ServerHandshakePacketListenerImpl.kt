package io.github.leaflowmc.leaflow.server.packets

import io.github.leaflowmc.leaflow.protocol.ProtocolStage
import io.github.leaflowmc.leaflow.protocol.listener.server.ServerHandshakePacketListener
import io.github.leaflowmc.leaflow.protocol.packets.handshake.ServerboundHandshakePacket
import io.github.leaflowmc.leaflow.protocol.packets.type.getServerProtocolFor
import io.github.leaflowmc.leaflow.server.LeaflowServerImpl
import io.github.leaflowmc.leaflow.server.player.PlayerConnection

class ServerHandshakePacketListenerImpl(
    val playerConnection: PlayerConnection
) : ServerHandshakePacketListener {
    override fun handshake(packet: ServerboundHandshakePacket) {
        val stage = when (packet.next) {
            ServerboundHandshakePacket.Intent.LOGIN -> ProtocolStage.LOGIN
            ServerboundHandshakePacket.Intent.STATUS -> ProtocolStage.STATUS
            ServerboundHandshakePacket.Intent.TRANSFER -> TODO("transfer is not implemented")

            ServerboundHandshakePacket.Intent.NONE -> error("illegal enum.")
        }

        playerConnection.setInboundProtocol(stage)
        playerConnection.setOutboundProtocol(stage)
    }
}