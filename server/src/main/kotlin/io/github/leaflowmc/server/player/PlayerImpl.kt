package io.github.leaflowmc.server.player

import io.github.leaflowmc.protocol.packets.ClientPacket
import io.github.leaflowmc.protocol.packets.ProtocolStage
import io.ktor.network.sockets.*
import kotlinx.coroutines.channels.Channel

open class PlayerImpl(
    private val socket: Socket,
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