package io.github.leaflowmc.leaflow.server

import io.github.leaflowmc.leaflow.protocol.ProtocolStage
import io.github.leaflowmc.leaflow.protocol.listener.server.ServerHandshakePacketListener
import io.github.leaflowmc.leaflow.protocol.listener.server.ServerLoginPacketListener
import io.github.leaflowmc.leaflow.protocol.listener.server.ServerPacketListener
import io.github.leaflowmc.leaflow.protocol.listener.server.ServerStatusPacketListener
import io.github.leaflowmc.leaflow.server.player.Player
import io.github.leaflowmc.leaflow.server.player.PlayerConnection

interface LeaflowFactory {
    fun createPlayer(networkState: PlayerConnection): Player
    fun createPlayerConnection(server: LeaflowServer, protocol: ProtocolStage): PlayerConnection

    fun createServerHandshakePacketListener(connection: PlayerConnection): ServerHandshakePacketListener
    fun createServerStatusPacketListener(connection: PlayerConnection): ServerStatusPacketListener
    fun createServerLoginPacketListener(connection: PlayerConnection): ServerLoginPacketListener

    fun createServerPacketListenerFor(stage: ProtocolStage, connection: PlayerConnection): ServerPacketListener {
        return when (stage) {
            ProtocolStage.HANDSHAKE -> createServerHandshakePacketListener(connection)
            ProtocolStage.STATUS -> createServerStatusPacketListener(connection)
            ProtocolStage.LOGIN -> createServerLoginPacketListener(connection)
            ProtocolStage.CONFIGURATION -> TODO("configuration not implemented")
            ProtocolStage.PLAY -> TODO("play not implemented")
        }
    }
}
