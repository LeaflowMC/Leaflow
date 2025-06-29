package io.github.leaflowmc.leaflow.protocol.packets

import io.github.leaflowmc.leaflow.protocol.listener.PacketListener

interface Packet<L : PacketListener> {
    fun handle(listener: L)

    /**
     * If the protocol needs to be switched after sending/receiving this packet
     */
    val terminal: Boolean get() = false
}