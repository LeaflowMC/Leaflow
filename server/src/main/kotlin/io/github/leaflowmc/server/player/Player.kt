package io.github.leaflowmc.server.player

import io.github.leaflowmc.protocol.packets.ClientPacket
import io.github.leaflowmc.protocol.ProtocolStage

interface Player {
    var protocolStage: ProtocolStage

    /**
     * Sends the packet to the player.
     */
    suspend fun sendPacket(packet: ClientPacket)
}