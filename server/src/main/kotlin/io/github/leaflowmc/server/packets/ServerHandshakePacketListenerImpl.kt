package io.github.leaflowmc.server.packets

import io.github.leaflowmc.protocol.ProtocolStage
import io.github.leaflowmc.protocol.listener.server.ServerHandshakePacketListener
import io.github.leaflowmc.protocol.packets.handshake.ServerboundHandshakePacket
import io.github.leaflowmc.protocol.packets.type.getServerProtocolFor
import io.github.leaflowmc.server.LeaflowServerImpl
import io.github.leaflowmc.server.player.PlayerConnection

class ServerHandshakePacketListenerImpl(
    val playerConnection: PlayerConnection
) : ServerHandshakePacketListener {
    override fun handshake(packet: ServerboundHandshakePacket) {
        when (packet.next) {
            ServerboundHandshakePacket.Intent.LOGIN -> playerConnection.protocol = ProtocolStage.LOGIN
            ServerboundHandshakePacket.Intent.STATUS -> playerConnection.protocol = ProtocolStage.STATUS
            ServerboundHandshakePacket.Intent.TRANSFER -> TODO("transfer is not implemented")

            ServerboundHandshakePacket.Intent.NONE -> error("illegal enum.")
        }
    }
}