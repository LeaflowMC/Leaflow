package io.github.leaflowmc.server

import io.github.leaflowmc.protocol.packets.ServerPacketListener
import io.github.leaflowmc.server.player.Player
import io.github.leaflowmc.server.player.PlayerImpl
import io.github.leaflowmc.server.player.ServerPacketListenerImpl
import io.ktor.network.sockets.Socket

open class LeaflowFactoryImpl protected constructor() : LeaflowFactory {
    companion object Instance : LeaflowFactoryImpl()

    override fun createServerPacketListener(player: Player): ServerPacketListener {
        return ServerPacketListenerImpl(player)
    }

    override fun createPlayer(socket: Socket): Player {
        return PlayerImpl(socket)
    }
}