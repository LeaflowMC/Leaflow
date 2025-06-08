package io.github.leaflowmc.protocol.packets

import io.github.leaflowmc.protocol.listener.client.ClientPacketListener

interface ClientPacket<L : ClientPacketListener, T : ClientPacket<L, T>> : Packet<L, T> {
}