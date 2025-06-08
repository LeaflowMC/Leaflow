package io.github.leaflowmc.server

import io.github.leaflowmc.protocol.packets.ClientPacket
import io.github.leaflowmc.protocol.ServerPacketListener
import io.github.leaflowmc.server.player.Player
import io.github.leaflowmc.server.player.PlayerImpl
import io.github.leaflowmc.server.player.ServerPacketListenerImpl
import io.ktor.network.sockets.Socket
import kotlinx.coroutines.channels.Channel

open class LeaflowFactoryImpl protected constructor() : LeaflowFactory {
    companion object Instance : LeaflowFactoryImpl()

    override fun createServerPacketListener(player: Player): ServerPacketListener {
        return ServerPacketListenerImpl(player)
    }

    override fun createPlayer(packetsChannel: Channel<ClientPacket>): Player {
        return PlayerImpl(packetsChannel)
    }
}