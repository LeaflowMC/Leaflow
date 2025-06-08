package io.github.leaflowmc.server

import io.github.leaflowmc.protocol.ProtocolStage
import io.github.leaflowmc.protocol.listener.server.ServerHandshakePacketListener
import io.github.leaflowmc.protocol.listener.server.ServerStatusPacketListener
import io.github.leaflowmc.server.netty.PlayerConnectionImpl
import io.github.leaflowmc.server.packets.ServerHandshakePacketListenerImpl
import io.github.leaflowmc.server.packets.ServerStatusPacketListenerImpl
import io.github.leaflowmc.server.player.Player
import io.github.leaflowmc.server.player.PlayerConnection
import io.github.leaflowmc.server.player.PlayerImpl

open class LeaflowFactoryImpl protected constructor() : LeaflowFactory {
    companion object Instance : LeaflowFactoryImpl()

    override fun createPlayer(networkState: PlayerConnection): Player {
        return PlayerImpl(networkState)
    }

    override fun createPlayerConnection(server: LeaflowServer, protocol: ProtocolStage): PlayerConnection {
        return PlayerConnectionImpl(server, protocol)
    }

    override fun createServerHandshakePacketListener(connection: PlayerConnection): ServerHandshakePacketListener {
        return ServerHandshakePacketListenerImpl(connection)
    }

    override fun createServerStatusPacketListener(connection: PlayerConnection): ServerStatusPacketListener {
        return ServerStatusPacketListenerImpl(connection)
    }
}