package io.github.leaflowmc.protocol.packets

import io.github.leaflowmc.protocol.ClientPacketListener

interface ClientPacket {
    suspend fun handle(listener: ClientPacketListener)
}