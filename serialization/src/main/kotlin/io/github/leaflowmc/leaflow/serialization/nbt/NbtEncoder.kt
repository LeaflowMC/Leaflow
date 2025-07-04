@file:OptIn(ExperimentalSerializationApi::class)

package io.github.leaflowmc.leaflow.serialization.nbt

import io.github.leaflowmc.leaflow.common.utils.toBinaryTag
import io.github.leaflowmc.leaflow.serialization.classDiscriminator
import kotlinx.serialization.*
import kotlinx.serialization.builtins.ByteArraySerializer
import kotlinx.serialization.builtins.IntArraySerializer
import kotlinx.serialization.builtins.LongArraySerializer
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.descriptors.StructureKind
import kotlinx.serialization.encoding.AbstractEncoder
import kotlinx.serialization.encoding.CompositeEncoder
import kotlinx.serialization.internal.AbstractPolymorphicSerializer
import kotlinx.serialization.modules.EmptySerializersModule
import kotlinx.serialization.modules.SerializersModule
import net.kyori.adventure.nbt.*
import java.util.*

abstract class NbtEncoder : AbstractEncoder() {
    abstract fun encodeNbt(value: BinaryTag)

    final override fun encodeValue(value: Any) = encodeNbt(value.toBinaryTag())
    final override fun encodeNull() {}

    final override fun beginStructure(descriptor: SerialDescriptor): CompositeEncoder {
        return when (descriptor.kind) {
            StructureKind.LIST -> {
                ListEncoder(serializersModule, ::encodeNbt)
            }
            StructureKind.CLASS,
            StructureKind.MAP,
            StructureKind.OBJECT -> {
                CompoundEncoder(serializersModule, ::encodeNbt)
            }

            else -> this
        }
    }

    @Suppress("UNCHECKED_CAST")
    @OptIn(InternalSerializationApi::class)
    final override fun <T> encodeSerializableValue(serializer: SerializationStrategy<T>, value: T) {
        if (serializer is AbstractPolymorphicSerializer<*>) {
            val actual = (serializer as AbstractPolymorphicSerializer<Any>)
                .findPolymorphicSerializer(this, value!!)

            val encoder = PrimitiveNbtEncoder(serializersModule)
            actual.serialize(encoder, value)

            val nbt = encoder.nbt
            require(nbt is CompoundBinaryTag) { "Can only serialize compounds polymorphically, but got ${nbt::class.simpleName}" }

            encodeNbt(
                nbt.put(
                    serializer.descriptor.classDiscriminator(),
                    actual.descriptor.serialName.toBinaryTag()
                )
            )
            return
        }

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

