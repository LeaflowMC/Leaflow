package io.github.leaflowmc.serialization.minecraft_format

import io.github.leaflowmc.common.utils.VarInt
import io.github.leaflowmc.common.utils.readPrefixedString
import io.github.leaflowmc.common.utils.readVarInt
import io.netty.buffer.ByteBuf
import kotlinx.serialization.DeserializationStrategy
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.AbstractDecoder
import kotlinx.serialization.encoding.CompositeDecoder.Companion.DECODE_DONE
import kotlinx.serialization.modules.EmptySerializersModule
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.serializer
import java.io.InputStream

val varIntSerializer = serializer<VarInt>()

@OptIn(ExperimentalSerializationApi::class)
class ByteBufMinecraftDecoder(
    val buffer: ByteBuf,
    override val serializersModule: SerializersModule
) : AbstractDecoder() {
    var counter = 0

    override fun decodeByte(): Byte = buffer.readByte()
    override fun decodeBoolean(): Boolean = buffer.readBoolean()
    override fun decodeShort(): Short = buffer.readShort()
    override fun decodeInt(): Int = buffer.readInt()
    override fun decodeLong(): Long = buffer.readLong()
    override fun decodeFloat(): Float = buffer.readFloat()
    override fun decodeDouble(): Double = buffer.readDouble()
    override fun decodeChar(): Char = buffer.readChar()
    override fun decodeString(): String = buffer.readPrefixedString()
    override fun decodeEnum(enumDescriptor: SerialDescriptor): Int = buffer.readVarInt()
    fun decodeVarInt() = buffer.readVarInt()

    override fun decodeElementIndex(descriptor: SerialDescriptor): Int {
        if (counter >= descriptor.elementsCount) return DECODE_DONE
        return counter++
    }

    override fun decodeSequentially(): Boolean = true

    @Suppress("UNCHECKED_CAST") // unchecked cast my beloved
    override fun <T> decodeSerializableValue(deserializer: DeserializationStrategy<T>): T {
        return when (deserializer.descriptor) {
            varIntSerializer.descriptor -> {
                VarInt(decodeVarInt()) as T
            }
            else -> super.decodeSerializableValue(deserializer)
        }
    }

    override fun decodeNotNullMark(): Boolean = decodeBoolean()
}

fun <T>ByteBuf.decodePacket(serializer: KSerializer<T>): T {
    return ByteBufMinecraftDecoder(this, EmptySerializersModule())
        .decodeSerializableValue(serializer)
}