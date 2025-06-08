package io.github.leaflowmc.server

import io.github.leaflowmc.protocol.packets.ClientPacket
import io.github.leaflowmc.protocol.packets.ServerPacketListener
import io.github.leaflowmc.server.player.Player
import io.ktor.network.sockets.Socket
import kotlinx.coroutines.channels.Channel

interface LeaflowFactory {
    fun createServerPacketListener(player: Player): ServerPacketListener
    fun createPlayer(socket: Socket, packetsChannel: Channel<ClientPacket>): Player
}