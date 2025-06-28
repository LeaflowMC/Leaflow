package io.github.leaflowmc.leaflow.server

import io.github.leaflowmc.leaflow.protocol.ProtocolStage
import io.github.leaflowmc.leaflow.protocol.listener.server.ServerHandshakePacketListener
import io.github.leaflowmc.leaflow.protocol.listener.server.ServerLoginPacketListener
import io.github.leaflowmc.leaflow.protocol.listener.server.ServerStatusPacketListener
import io.github.leaflowmc.leaflow.server.netty.PlayerConnectionImpl
import io.github.leaflowmc.leaflow.server.packets.ServerHandshakePacketListenerImpl
import io.github.leaflowmc.leaflow.server.packets.ServerLoginPacketListenerImpl
import io.github.leaflowmc.leaflow.server.packets.ServerStatusPacketListenerImpl
import io.github.leaflowmc.leaflow.server.player.Player
import io.github.leaflowmc.leaflow.server.player.PlayerConnection
import io.github.leaflowmc.leaflow.server.player.PlayerImpl

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

    override fun createServerLoginPacketListener(connection: PlayerConnection): ServerLoginPacketListener {
        return ServerLoginPacketListenerImpl(connection)
    }
}