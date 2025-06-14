package io.github.leaflowmc.leaflow.server.packets

import io.github.leaflowmc.leaflow.protocol.listener.server.ServerStatusPacketListener
import io.github.leaflowmc.leaflow.protocol.packets.ping.AbstractServerboundPingRequestPacket
import io.github.leaflowmc.leaflow.protocol.packets.ping.ClientboundStatusPongResponsePacket
import io.github.leaflowmc.leaflow.protocol.packets.status.ClientboundStatusResponsePacket
import io.github.leaflowmc.leaflow.protocol.packets.status.ServerboundStatusRequestPacket
import io.github.leaflowmc.leaflow.protocol.packets.status.StatusResponse
import io.github.leaflowmc.leaflow.server.player.PlayerConnection

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