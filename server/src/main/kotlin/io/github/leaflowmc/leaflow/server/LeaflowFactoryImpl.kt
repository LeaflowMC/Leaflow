package io.github.leaflowmc.leaflow.server

import io.github.leaflowmc.leaflow.common.GameProfile
import io.github.leaflowmc.leaflow.protocol.ProtocolStage
import io.github.leaflowmc.leaflow.protocol.listener.server.ServerHandshakePacketListener
import io.github.leaflowmc.leaflow.protocol.listener.server.ServerLoginPacketListener
import io.github.leaflowmc.leaflow.protocol.listener.server.ServerStatusPacketListener
import io.github.leaflowmc.leaflow.server.netty.PlayerConnectionImpl
import io.github.leaflowmc.leaflow.server.packets.ServerConfigurationPacketListenerImpl
import io.github.leaflowmc.leaflow.server.packets.ServerHandshakePacketListenerImpl
import io.github.leaflowmc.leaflow.server.packets.ServerLoginPacketListenerImpl
import io.github.leaflowmc.leaflow.server.packets.ServerPlayPacketListenerImpl
import io.github.leaflowmc.leaflow.server.packets.ServerStatusPacketListenerImpl
import io.github.leaflowmc.leaflow.server.packets.api.LeaflowServerConfigurationPacketListener
import io.github.leaflowmc.leaflow.server.packets.api.LeaflowServerPlayPacketListener
import io.github.leaflowmc.leaflow.server.player.Player
import io.github.leaflowmc.leaflow.server.player.PlayerConnection
import io.github.leaflowmc.leaflow.server.player.PlayerImpl

open class LeaflowFactoryImpl protected constructor() : LeaflowFactory {
    companion object Instance : LeaflowFactoryImpl()

    override fun createPlayer(networkState: PlayerConnection, gameProfile: GameProfile): Player {
        return PlayerImpl(networkState, gameProfile)
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

    override fun createServerConfigurationPacketListener(connection: PlayerConnection): LeaflowServerConfigurationPacketListener {
        return ServerConfigurationPacketListenerImpl(connection)
    }

    override fun createServerPlayPacketListener(connection: PlayerConnection): LeaflowServerPlayPacketListener {
        return ServerPlayPacketListenerImpl(connection)
    }
}