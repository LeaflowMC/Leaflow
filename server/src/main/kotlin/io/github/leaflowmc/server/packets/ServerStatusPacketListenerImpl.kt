package io.github.leaflowmc.server.packets

import io.github.leaflowmc.protocol.listener.server.ServerStatusPacketListener
import io.github.leaflowmc.protocol.packets.status.ServerboundStatusRequestPacket
import io.github.leaflowmc.server.player.PlayerConnection

class ServerStatusPacketListenerImpl(
    val playerConnection: PlayerConnection
) : ServerStatusPacketListener {
    override fun statusRequest(packet: ServerboundStatusRequestPacket) {
    }
}