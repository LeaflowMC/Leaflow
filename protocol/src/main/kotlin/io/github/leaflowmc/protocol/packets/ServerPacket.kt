package io.github.leaflowmc.protocol.packets

import io.github.leaflowmc.protocol.ServerPacketListener

interface ServerPacket {
    suspend fun handle(listener: ServerPacketListener)
}