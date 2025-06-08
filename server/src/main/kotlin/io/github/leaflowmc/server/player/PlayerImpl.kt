package io.github.leaflowmc.server.player

import io.github.leaflowmc.protocol.ProtocolStage
import io.github.leaflowmc.protocol.packets.ClientPacket
import kotlinx.coroutines.channels.Channel

open class PlayerImpl(
    private val packetsChannel: Channel<ClientPacket>
) : Player {
    override var protocolStage = ProtocolStage.HANDSHAKE

    /**
     * Sends the packet to the packets channel,
     * possibly suspending until someone receives it
     *
     * @see Player.sendPacket
     */
    override suspend fun sendPacket(packet: ClientPacket) {
        packetsChannel.send(packet)
    }
}