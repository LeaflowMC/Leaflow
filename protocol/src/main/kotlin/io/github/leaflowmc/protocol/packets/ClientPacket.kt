package io.github.leaflowmc.protocol.packets

interface ClientPacket {
    suspend fun handle(listener: ClientPacketListener)
}