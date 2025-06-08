package io.github.leaflowmc.server.player

import io.github.leaflowmc.protocol.packets.ClientPacket

interface Player {
    val connection: PlayerConnection

    fun sendPacket(packet: ClientPacket<*, *>) = connection.sendPacket(packet)
}