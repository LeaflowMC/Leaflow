@file:OptIn(ExperimentalSerializationApi::class)

package io.github.leaflowmc.leaflow.serialization.nbt

import io.github.leaflowmc.leaflow.common.utils.getPrimitive
import io.github.leaflowmc.leaflow.serialization.ArrayDecoder
import kotlinx.serialization.DeserializationStrategy
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.SerializationException
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.descriptors.StructureKind
import kotlinx.serialization.encoding.AbstractDecoder
import kotlinx.serialization.encoding.CompositeDecoder
import kotlinx.serialization.modules.EmptySerializersModule
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.serializer
import net.kyori.adventure.nbt.*
import java.util.*

abstract class NbtDecoder : AbstractDecoder() {
    abstract fun decodeNbt(): BinaryTag

    final override fun decodeValue(): Any {
        return checkNotNull(decodeNbt().getPrimitive()) { "Is not a primitive" }
    }

    final override fun beginStructure(descriptor: SerialDescriptor): CompositeDecoder {
        val nbt = decodeNbt()

        return when (descriptor.kind) {
             StructureKind.LIST -> {
                when (nbt) {
                    is ListBinaryTag -> ListDecoder(nbt, serializersModule)
                    is ByteArrayBinaryTag -> ArrayDecoder(nbt.value().toTypedArray(), serializersModule)
                    is IntArrayBinaryTag -> ArrayDecoder(nbt.value().toTypedArray(), serializersModule)
                    is LongArrayBinaryTag -> ArrayDecoder(nbt.value().toTypedArray(), serializersModule)

                    else -> throw SerializationException("trying to decode a list structure, but nbt tag is not a list")
                }
            }

            StructureKind.MAP,
            StructureKind.OBJECT,
            StructureKind.CLASS -> {
                if (nbt !is CompoundBinaryTag) {
                    throw SerializationException("trying to decode a compound structure, but nbt tag is not a compound")
                }

                CompoundDecoder(nbt, serializersModule)
            }

            else -> this
        }
    }
}

class PrimitiveNbtDecoder(
    private val nbt: BinaryTag,
    override val serializersModule: SerializersModule
) : NbtDecoder() {
    override fun decodeNbt(): BinaryTag = nbt

    override fun decodeElementIndex(descriptor: SerialDescriptor): Int {
        throw SerializationException("there's no index unless there's a structure")
    }
}

class CompoundDecoder(
    private val nbt: CompoundBinaryTag,
    override val serializersModule: SerializersModule
) : NbtDecoder() {
    private val keys = ArrayDeque(nbt.keySet())

    override fun decodeNbt(): BinaryTag = nbt.get(keys.removeFirst())!!

    override fun decodeElementIndex(descriptor: SerialDescriptor): Int {
        if (keys.isEmpty()) return CompositeDecoder.DECODE_DONE

        return descriptor.getElementIndex(keys.first)
    }

    override fun decodeNull(): Nothing? = null
}

class ListDecoder(
    nbt: ListBinaryTag,
    override val serializersModule: SerializersModule
) : NbtDecoder() {
    private var counter = 0
    private val list = ArrayDeque(nbt.toList())
    private val size = nbt.size()

    override fun decodeNbt(): BinaryTag = list.pop()

    override fun decodeElementIndex(descriptor: SerialDescriptor): Int {
        if (counter >= size) return CompositeDecoder.DECODE_DONE

        return counter++
    }

    override fun decodeSequentially(): Boolean = true
    override fun decodeCollectionSize(descriptor: SerialDescriptor): Int = size

}

fun <T> decodeFromNbt(serializer: DeserializationStrategy<T>, tag: BinaryTag): T {
    return PrimitiveNbtDecoder(tag, EmptySerializersModule())
        .decodeSerializableValue(serializer)
}

inline fun <reified T>decodeFromNbt(tag: BinaryTag): T = decodeFromNbt(serializer(), tag)

