package io.github.leaflowmc.leaflow.server.packets.plugin_message

import it.unimi.dsi.fastutil.objects.Object2ObjectArrayMap
import it.unimi.dsi.fastutil.objects.Object2ObjectMap
import kotlinx.serialization.KSerializer
import kotlinx.serialization.serializer
import kotlin.reflect.KClass

class PluginMessages(
    id2Serializer: Object2ObjectMap<String, KSerializer<out PluginMessage>>,
    class2Id: Object2ObjectMap<KClass<out PluginMessage>, String>
) {
    private val id2Serializer = Object2ObjectArrayMap(id2Serializer)
    private val class2Id = Object2ObjectArrayMap(class2Id)

    init {
        id2Serializer.defaultReturnValue(null)
        class2Id.defaultReturnValue(null)
    }

    operator fun get(id: String): KSerializer<out PluginMessage>? {
        return id2Serializer[id]
    }

    operator fun get(clazz: KClass<out PluginMessage>): String? {
        return class2Id[clazz]
    }

    class Builder {
        private val id2Serializer = Object2ObjectArrayMap<String, KSerializer<out PluginMessage>>()
        private val class2Id = Object2ObjectArrayMap<KClass<out PluginMessage>, String>()

        init {
            // default plugin messages
            addPluginMessage<BrandPluginMessage>("minecraft:brand")
        }

        inline fun <reified T : PluginMessage> addPluginMessage(id: String) {
            addPluginMessage(id, serializer<T>(), T::class)
        }

        fun <T : PluginMessage> addPluginMessage(id: String, seri: KSerializer<T>, clazz: KClass<T>) {
            id2Serializer.putIfAbsent(id, seri)
            class2Id.putIfAbsent(clazz, id)
        }

        fun build(): PluginMessages {
            return PluginMessages(id2Serializer, class2Id)
        }
    }
}