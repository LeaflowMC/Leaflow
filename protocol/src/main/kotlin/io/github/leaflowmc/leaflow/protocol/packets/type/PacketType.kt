package io.github.leaflowmc.leaflow.protocol.packets.type

import io.github.leaflowmc.leaflow.protocol.packets.Packet
import kotlinx.serialization.KSerializer

data class PacketType<T : Packet<*, out T>>(
    val id: Int,
    val serializer: KSerializer<out T>
)