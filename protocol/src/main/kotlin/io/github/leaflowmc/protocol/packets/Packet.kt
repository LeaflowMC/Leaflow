package io.github.leaflowmc.protocol.packets

import io.github.leaflowmc.protocol.listener.PacketListener
import io.github.leaflowmc.protocol.packets.type.PacketType

interface Packet<L : PacketListener, T : Packet<L, T>> {
    fun getType(): PacketType<T>
    fun handle(listener: L)
    val terminal: Boolean get() = false
}