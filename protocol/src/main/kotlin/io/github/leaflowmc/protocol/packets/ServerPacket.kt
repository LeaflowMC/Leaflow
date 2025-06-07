package io.github.leaflowmc.protocol.packets

interface ServerPacket {
    suspend fun handle(listener: ServerPacketListener)
}