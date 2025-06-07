package io.github.leaflowmc.serialization

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.AbstractDecoder
import kotlinx.serialization.encoding.CompositeDecoder.Companion.DECODE_DONE
import kotlinx.serialization.modules.SerializersModule

@OptIn(ExperimentalSerializationApi::class)
abstract class MinecraftProtocolDecoder(
    override val serializersModule: SerializersModule
) : AbstractDecoder() {
    private var counter = 0

    override fun decodeElementIndex(descriptor: SerialDescriptor): Int {
        // index is not encoded. all fields are in a strict order
        if(counter >= descriptor.elementsCount) return DECODE_DONE
        return counter++
    }

    abstract override fun decodeBoolean(): Boolean
    abstract override fun decodeByte(): Byte
    abstract override fun decodeShort(): Short
    abstract override fun decodeInt(): Int
    abstract override fun decodeLong(): Long
    abstract override fun decodeFloat(): Float
    abstract override fun decodeDouble(): Double
    abstract override fun decodeChar(): Char
    abstract override fun decodeString(): String
    abstract override fun decodeEnum(enumDescriptor: SerialDescriptor): Int
}