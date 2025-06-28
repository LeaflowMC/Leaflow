package io.github.leaflowmc.leaflow.protocol.listener.server

import io.github.leaflowmc.leaflow.protocol.packets.ping.ServerboundPingPacket

interface ServerPingPacketListener : ServerPacketListener {
    fun pingRequest(packet: ServerboundPingPacket<*, *>)
}