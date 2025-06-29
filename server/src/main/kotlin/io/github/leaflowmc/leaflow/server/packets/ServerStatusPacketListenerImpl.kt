package io.github.leaflowmc.leaflow.server.packets

import io.github.leaflowmc.leaflow.protocol.listener.server.ServerStatusPacketListener
import io.github.leaflowmc.leaflow.protocol.packets.ping.ClientboundPongPacket
import io.github.leaflowmc.leaflow.protocol.packets.ping.ServerboundPingPacket
import io.github.leaflowmc.leaflow.protocol.packets.status.ClientboundStatusResponsePacket
import io.github.leaflowmc.leaflow.protocol.packets.status.ServerboundStatusRequestPacket
import io.github.leaflowmc.leaflow.protocol.packets.status.StatusResponse
import io.github.leaflowmc.leaflow.server.player.PlayerConnection

class ServerStatusPacketListenerImpl(
    val playerConnection: PlayerConnection
) : ServerStatusPacketListener {
    override fun pingRequest(packet: ServerboundPingPacket) {
        playerConnection.sendPacket(ClientboundPongPacket(packet.timestamp))
    }

    override fun statusRequest(packet: ServerboundStatusRequestPacket) {
        playerConnection.sendPacket(
            ClientboundStatusResponsePacket(
                StatusResponse(
                    StatusResponse.Version("1.21.5", 770),
                    false,
                    playerConnection.server.motd,
                )
            )
        )
    }
}