package io.github.leaflowmc.leaflow.protocol.listener.server

import io.github.leaflowmc.leaflow.protocol.packets.common.ServerboundKeepAlivePacket
import io.github.leaflowmc.leaflow.protocol.packets.ping.ServerboundPongPacket

interface ServerCommonPacketListener : ServerPacketListener {
    fun keepAlive(packet: ServerboundKeepAlivePacket)
    fun pong(packet: ServerboundPongPacket)
}