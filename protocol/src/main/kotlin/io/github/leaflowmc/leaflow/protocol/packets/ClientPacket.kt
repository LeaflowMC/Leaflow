package io.github.leaflowmc.leaflow.protocol.packets

import io.github.leaflowmc.leaflow.protocol.listener.client.ClientPacketListener

interface ClientPacket<L : ClientPacketListener, T : ClientPacket<L, T>> : Packet<L, T> {
}