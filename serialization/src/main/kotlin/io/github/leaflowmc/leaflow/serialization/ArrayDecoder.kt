package io.github.leaflowmc.leaflow.serialization

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.AbstractDecoder
import kotlinx.serialization.encoding.CompositeDecoder
import kotlinx.serialization.modules.SerializersModule

@OptIn(ExperimentalSerializationApi::class)
class ArrayDecoder<T : Any>(
    val array: Array<T>,
    override val serializersModule: SerializersModule
) : AbstractDecoder() {
    private var index = 0

    override fun decodeElementIndex(descriptor: SerialDescriptor): Int {
        if (index >= array.size) return CompositeDecoder.Companion.DECODE_DONE

        return index
    }

    override fun decodeSequentially(): Boolean = true
    override fun decodeCollectionSize(descriptor: SerialDescriptor): Int = array.size

    override fun decodeValue(): Any {
        return array[index++]
    }
}