package io.github.leaflowmc.protocol.listener.server

import io.github.leaflowmc.protocol.packets.ping.AbstractServerboundPingRequestPacket

interface ServerPingPacketListener : ServerPacketListener {
    fun pingRequest(packet: AbstractServerboundPingRequestPacket<*, *>)
}