package io.github.leaflowmc.leaflow.protocol.packets.type

import io.github.leaflowmc.leaflow.protocol.ProtocolStage
import io.github.leaflowmc.leaflow.protocol.packets.Packet
import it.unimi.dsi.fastutil.ints.Int2ObjectArrayMap
import it.unimi.dsi.fastutil.ints.Int2ObjectMap
import it.unimi.dsi.fastutil.objects.Object2IntArrayMap
import it.unimi.dsi.fastutil.objects.Object2IntMap
import kotlinx.serialization.KSerializer
import kotlinx.serialization.serializer
import kotlin.reflect.KClass

class ProtocolInfo(
    id2Serializer: Int2ObjectMap<KSerializer<out Packet<*>>>,
    class2Id: Object2IntMap<KClass<out Packet<*>>>,
    skippedMap: Int2ObjectMap<String>,
    val protocolStage: ProtocolStage
) {
    private val id2Serializer = Int2ObjectArrayMap(id2Serializer)
    private val class2Id = Object2IntArrayMap(class2Id)
    private val skippedMap = Int2ObjectArrayMap(skippedMap)

    init {
        this.id2Serializer.defaultReturnValue(null)
        this.class2Id.defaultReturnValue(-1)
        this.skippedMap.defaultReturnValue(null)
    }

    /**
     * Returns the serializer of the packet, or null if this id is absent in this protocol info
     */
    operator fun get(id: Int): KSerializer<out Packet<*>>? {
        return id2Serializer.get(id)
    }

    /**
     * Returns the id of the packet, or -1 if this packet is absent in this protocol info
     */
    operator fun get(clazz: KClass<out Packet<*>>): Int {
        return class2Id.getInt(clazz)
    }

    /**
     * Returns the name of the skipped packet, if its skipped
     *
     * Null otherwise
     */
    fun getSkippedName(id: Int): String? {
        return skippedMap[id]
    }
}

class ProtocolInfoBuilder(private val protocolStage: ProtocolStage) {
    private val id2Serializer = Int2ObjectArrayMap<KSerializer<out Packet<*>>>()
    private val class2Id = Object2IntArrayMap<KClass<out Packet<*>>>()
    private val skippedMap = Int2ObjectArrayMap<String>()
    private var index = 0

    fun <T : Packet<*>> addPacket(clazz: KClass<T>, serializer: KSerializer<T>) {
        id2Serializer[index] = serializer
        class2Id[clazz] = index
        ++index
    }

    inline fun <reified T : Packet<*>> addPacket() {
        addPacket(T::class, serializer<T>())
    }

    fun skipPacket(name: String? = null) {
        if (name != null) {
            skippedMap[index] = name
        }
        ++index
    }

    fun build(): ProtocolInfo {
        return ProtocolInfo(
            id2Serializer,
            class2Id,
            skippedMap,
            protocolStage
        )
    }
}

fun createProtocolInfo(protocolStage: ProtocolStage, block: ProtocolInfoBuilder.() -> Unit): ProtocolInfo {
    return ProtocolInfoBuilder(protocolStage).apply(block).build()
}
