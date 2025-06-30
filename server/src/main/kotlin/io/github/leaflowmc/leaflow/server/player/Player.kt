package io.github.leaflowmc.leaflow.server.player

import io.github.leaflowmc.leaflow.common.GameProfile
import io.github.leaflowmc.leaflow.protocol.packets.ClientPacket

interface Player {
    val connection: PlayerConnection
    val profile: GameProfile
}

fun Player.sendPacket(packet: ClientPacket<*>) = connection.sendPacket(packet)
