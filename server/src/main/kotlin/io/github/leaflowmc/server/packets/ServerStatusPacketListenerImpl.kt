package io.github.leaflowmc.server.packets

import io.github.leaflowmc.protocol.listener.server.ServerStatusPacketListener
import io.github.leaflowmc.protocol.packets.ping.AbstractServerboundPingRequestPacket
import io.github.leaflowmc.protocol.packets.ping.ClientboundStatusPongResponsePacket
import io.github.leaflowmc.protocol.packets.status.ClientboundStatusResponsePacket
import io.github.leaflowmc.protocol.packets.status.ServerboundStatusRequestPacket
import io.github.leaflowmc.protocol.packets.status.StatusResponse
import io.github.leaflowmc.server.player.PlayerConnection

class ServerStatusPacketListenerImpl(
    val playerConnection: PlayerConnection
) : ServerStatusPacketListener {
    override fun pingRequest(packet: AbstractServerboundPingRequestPacket<*, *>) {
        playerConnection.sendPacket(ClientboundStatusPongResponsePacket(packet.timestamp))
    }

    override fun statusRequest(packet: ServerboundStatusRequestPacket) {
        playerConnection.sendPacket(
            ClientboundStatusResponsePacket(
                StatusResponse(
                    StatusResponse.Version("1.21.5", 770),
                    null, null, true
                )
            )
        )
    }
}