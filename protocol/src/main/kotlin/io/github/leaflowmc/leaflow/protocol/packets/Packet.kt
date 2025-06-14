package io.github.leaflowmc.leaflow.protocol.packets

import io.github.leaflowmc.leaflow.protocol.listener.PacketListener
import io.github.leaflowmc.leaflow.protocol.packets.type.PacketType

interface Packet<L : PacketListener, T : Packet<L, T>> {
    fun getType(): PacketType<T>
    fun handle(listener: L)

    /**
     * If the receiver of the packet should switch protocol after receiving this packet
     */
    val terminal: Boolean get() = false
}