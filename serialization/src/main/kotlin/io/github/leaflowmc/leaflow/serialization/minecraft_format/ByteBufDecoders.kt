@file:OptIn(ExperimentalSerializationApi::class)

package io.github.leaflowmc.leaflow.serialization.minecraft_format

import io.github.leaflowmc.leaflow.common.utils.VarInt
import io.github.leaflowmc.leaflow.serialization.annotations.NotLengthPrefixed
import io.netty.buffer.ByteBuf
import kotlinx.serialization.DeserializationStrategy
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.CompositeDecoder.Companion.DECODE_DONE
import kotlinx.serialization.modules.EmptySerializersModule
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.serializer

val varIntSerializer = serializer<VarInt>()

class ByteBufDecoder(
    override val buffer: ByteBuf,
    override val serializersModule: SerializersModule
) : AbstractByteBufDecoder() {
    override fun decodeElementIndex(descriptor: SerialDescriptor): Int {
        throw IllegalStateException("there's no index unless there's a structure")
    }
}

class ByteBufObjectDecoder(
    override val buffer: ByteBuf,
    override val serializersModule: SerializersModule
) : AbstractByteBufDecoder() {
    private var index: Int = 0

    override fun decodeElementIndex(descriptor: SerialDescriptor): Int {
        if (index >= descriptor.elementsCount) return DECODE_DONE
        return index++
    }
}

class ByteBufCollectionDecoder(
    override val buffer: ByteBuf,
    override val serializersModule: SerializersModule,
    annotations: Collection<Annotation>?
) : AbstractByteBufDecoder() {
    private var index = 0
    private val notLengthPrefixed: NotLengthPrefixed? = annotations
        ?.firstNotNullOfOrNull { it as? NotLengthPrefixed }

    override fun decodeElementIndex(descriptor: SerialDescriptor): Int {
        if (buffer.readableBytes() == 0 && notLengthPrefixed != null) {
            return DECODE_DONE
        }

        return index++
    }

    override fun decodeCollectionSize(descriptor: SerialDescriptor): Int {
        return notLengthPrefixed?.length ?: decodeVarInt()
    }

    override fun decodeSequentially(): Boolean {
        return notLengthPrefixed?.length != -1
    }
}

fun <T> ByteBuf.decode(deserializer: DeserializationStrategy<T>): T {
    return ByteBufDecoder(this, EmptySerializersModule())
        .decodeSerializableValue(deserializer)
}

inline fun <reified T> ByteBuf.decode(): T = decode(serializer())

