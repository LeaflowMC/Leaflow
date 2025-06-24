package io.github.leaflowmc.leaflow.serialization.nbt

import io.github.leaflowmc.leaflow.common.utils.toBinaryTag
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.SerializationStrategy
import kotlinx.serialization.builtins.ByteArraySerializer
import kotlinx.serialization.builtins.IntArraySerializer
import kotlinx.serialization.builtins.LongArraySerializer
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.descriptors.StructureKind
import kotlinx.serialization.encoding.AbstractEncoder
import kotlinx.serialization.encoding.CompositeEncoder
import kotlinx.serialization.modules.EmptySerializersModule
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.serializer
import net.kyori.adventure.nbt.*
import java.util.*

@OptIn(ExperimentalSerializationApi::class)
abstract class NbtEncoder : AbstractEncoder() {
    abstract fun encodeNbt(value: BinaryTag)

    final override fun encodeValue(value: Any) = encodeNbt(value.toBinaryTag())

    final override fun beginStructure(descriptor: SerialDescriptor): CompositeEncoder {
        if (descriptor.kind == StructureKind.LIST) {
            return ListEncoder(serializersModule, ::encodeNbt)
        }

        return CompoundEncoder(serializersModule, ::encodeNbt)
    }

    final override fun <T> encodeSerializableValue(serializer: SerializationStrategy<T>, value: T) {
        when (serializer.descriptor) {
            ByteArraySerializer().descriptor -> {
                encodeNbt(ByteArrayBinaryTag.byteArrayBinaryTag(*value as ByteArray))
            }
            IntArraySerializer().descriptor -> {
                encodeNbt(IntArrayBinaryTag.intArrayBinaryTag(*value as IntArray))
            }
            LongArraySerializer().descriptor -> {
                encodeNbt(LongArrayBinaryTag.longArrayBinaryTag(*value as LongArray))
            }

            else -> super.encodeSerializableValue(serializer, value)
        }
    }
}

class PrimitiveNbtEncoder(
    override val serializersModule: SerializersModule,
) : NbtEncoder() {
    var nbt: BinaryTag = CompoundBinaryTag.empty()
        private set

    override fun encodeNbt(value: BinaryTag) {
        nbt = value
    }
}

class CompoundEncoder(
    override val serializersModule: SerializersModule,
    val onEnd: (CompoundBinaryTag) -> Unit
) : NbtEncoder() {
    private val nbt = CompoundBinaryTag.builder()

    private val names = Stack<String>()

    override fun encodeElement(descriptor: SerialDescriptor, index: Int): Boolean {
        names.push(descriptor.getElementName(index))
        return true
    }

    override fun encodeNbt(value: BinaryTag) {
        nbt.put(names.pop(), value)
    }

    override fun endStructure(descriptor: SerialDescriptor) {
        onEnd(nbt.build())
    }
}

class ListEncoder(
    override val serializersModule: SerializersModule,
    private val onEnd: (ListBinaryTag) -> Unit
) : NbtEncoder() {
    private val list = ListBinaryTag.builder()

    override fun encodeNbt(value: BinaryTag) {
        list.add(value)
    }

    override fun endStructure(descriptor: SerialDescriptor) {
        onEnd(list.build())
    }
}

fun <T>encodeToNbt(serializer: SerializationStrategy<T>, value: T): BinaryTag {
    return PrimitiveNbtEncoder(EmptySerializersModule())
        .apply {
            encodeSerializableValue(serializer, value)
        }
        .nbt
}

inline fun <reified T> encodeToNbt(value: T): BinaryTag = encodeToNbt(serializer(), value)

