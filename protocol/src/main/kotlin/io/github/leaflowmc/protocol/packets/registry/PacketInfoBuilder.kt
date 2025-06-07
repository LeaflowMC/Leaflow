package io.github.leaflowmc.protocol.packets.registry

import kotlinx.serialization.KSerializer

@Target(AnnotationTarget.CLASS, AnnotationTarget.TYPE)
@DslMarker
annotation class PacketInfoDsl

@PacketInfoDsl
class PacketInfoBuilder<T> {
    private var counter = 0
    private val map = mutableMapOf<Int, KSerializer<out T>>()

    fun addPacket(packet: KSerializer<out T>) {
        map[counter++] = packet
    }

    fun skipPacket() {
        ++counter
    }

    fun build(): Map<Int, KSerializer<out T>> {
        return map.toMap()
    }
}

inline fun <T>createPacketInfo(block: @PacketInfoDsl PacketInfoBuilder<T>.() -> Unit): Map<Int, KSerializer<out T>> {
    return PacketInfoBuilder<T>().apply(block).build()
}