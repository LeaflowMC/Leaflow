package io.github.leaflowmc.protocol.packets

import io.github.leaflowmc.protocol.listener.server.ServerPacketListener

interface ServerPacket<L : ServerPacketListener, T : ServerPacket<L, T>> : Packet<L, T> {
}