package io.github.leaflowmc.protocol.packets.type

import io.github.leaflowmc.protocol.listener.PacketListener
import io.github.leaflowmc.protocol.packets.Packet
import kotlinx.serialization.KSerializer

open class ProtocolInfo<L : PacketListener, T : Packet<L, out T>> {
    fun getPacketType(id: Int): PacketType<out T>? {
        return packets[id]
    }

    private val packets = mutableListOf<PacketType<out T>?>()

    fun <S : Packet<*, out S>>addPacket(serializer: KSerializer<S>): PacketType<S> {
        val type = PacketType(
            packets.size,
            serializer
        )

        @Suppress("UNCHECKED_CAST")
        packets.add(type as PacketType<T>)

        return type
    }

    fun skipPacket() {
        packets.add(null)
    }
}