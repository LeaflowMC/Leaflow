package io.github.leaflowmc.leaflow.protocol.packets

import io.github.leaflowmc.leaflow.protocol.listener.server.ServerPacketListener

interface ServerPacket<L : ServerPacketListener> : Packet<L> {
}