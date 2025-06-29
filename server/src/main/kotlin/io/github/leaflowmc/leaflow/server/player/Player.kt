package io.github.leaflowmc.leaflow.server.player

import io.github.leaflowmc.leaflow.protocol.packets.ClientPacket

interface Player {
    val connection: PlayerConnection

    fun sendPacket(packet: ClientPacket<*>) = connection.sendPacket(packet)
}