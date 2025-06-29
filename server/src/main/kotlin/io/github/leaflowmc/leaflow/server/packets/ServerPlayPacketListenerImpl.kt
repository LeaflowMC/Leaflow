package io.github.leaflowmc.leaflow.server.packets

import io.github.leaflowmc.leaflow.protocol.packets.ping.ClientboundPongPacket
import io.github.leaflowmc.leaflow.protocol.packets.ping.ServerboundPingPacket
import io.github.leaflowmc.leaflow.server.packets.api.LeaflowServerPlayPacketListener
import io.github.leaflowmc.leaflow.server.player.PlayerConnection

class ServerPlayPacketListenerImpl(
    override val playerConnection: PlayerConnection
) : ServerCommonPacketListenerImpl(), LeaflowServerPlayPacketListener {
    override fun pingRequest(packet: ServerboundPingPacket) {
        playerConnection.sendPacket(ClientboundPongPacket(packet.timestamp))
    }
}