package io.github.leaflowmc.protocol.packets.registry

import kotlin.reflect.KClass

@Target(AnnotationTarget.CLASS, AnnotationTarget.TYPE)
@DslMarker
annotation class ClientPacketInfoDsl

@ClientPacketInfoDsl
class ClientPacketInfoBuilder<T : Any> {
    var counter = 0
    val map = mutableMapOf<KClass<out T>, Int>()

    inline fun <reified Y : T> addPacket() {
        map[Y::class] = counter++
    }

    fun skipPacket() {
        ++counter
    }

    fun build(): Map<KClass<out T>, Int> {
        return map.toMap()
    }
}

inline fun <T : Any> createClientPacketInfo(block: @ClientPacketInfoDsl ClientPacketInfoBuilder<T>.() -> Unit): Map<KClass<out T>, Int> {
    return ClientPacketInfoBuilder<T>().apply(block).build()
}