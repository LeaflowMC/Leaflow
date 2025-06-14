package io.github.leaflowmc.leaflow.protocol.listener.server

import io.github.leaflowmc.leaflow.protocol.packets.ping.AbstractServerboundPingRequestPacket

interface ServerPingPacketListener : ServerPacketListener {
    fun pingRequest(packet: AbstractServerboundPingRequestPacket<*, *>)
}