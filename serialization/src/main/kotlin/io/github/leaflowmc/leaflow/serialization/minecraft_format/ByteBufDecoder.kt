@file:OptIn(ExperimentalSerializationApi::class)

package io.github.leaflowmc.leaflow.serialization.minecraft_format

import io.github.leaflowmc.leaflow.common.utils.VarInt
import io.github.leaflowmc.leaflow.common.utils.readPrefixedString
import io.github.leaflowmc.leaflow.common.utils.readVarInt
import io.github.leaflowmc.leaflow.common.serializer.AnyToNbtSerializer
import io.github.leaflowmc.leaflow.serialization.annotations.ProtocolEnumKind
import io.github.leaflowmc.leaflow.serialization.annotations.protocolEnumKind
import io.github.leaflowmc.leaflow.serialization.nbt.decodeFromNbt
import io.netty.buffer.ByteBuf
import io.netty.buffer.ByteBufInputStream
import kotlinx.serialization.DeserializationStrategy
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.descriptors.StructureKind
import kotlinx.serialization.encoding.AbstractDecoder
import kotlinx.serialization.encoding.CompositeDecoder
import kotlinx.serialization.encoding.CompositeDecoder.Companion.DECODE_DONE
import kotlinx.serialization.modules.EmptySerializersModule
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.serializer
import net.kyori.adventure.nbt.BinaryTagIO
import java.io.ByteArrayOutputStream

val varIntSerializer = serializer<VarInt>()

abstract class AbstractByteBufDecoder : AbstractDecoder() {
    protected abstract val buffer: ByteBuf

    final override fun decodeByte(): Byte = buffer.readByte()
    final override fun decodeBoolean(): Boolean = buffer.readBoolean()
    final override fun decodeShort(): Short = buffer.readShort()
    final override fun decodeInt(): Int = buffer.readInt()
    final override fun decodeLong(): Long = buffer.readLong()
    final override fun decodeFloat(): Float = buffer.readFloat()
    final override fun decodeDouble(): Double = buffer.readDouble()
    final override fun decodeChar(): Char = buffer.readChar()
    final override fun decodeString(): String = buffer.readPrefixedString()
    final override fun decodeNotNullMark(): Boolean = decodeBoolean()
    final override fun decodeCollectionSize(descriptor: SerialDescriptor): Int = decodeVarInt()

    final override fun decodeEnum(enumDescriptor: SerialDescriptor): Int {
        return when(enumDescriptor.protocolEnumKind()) {
            ProtocolEnumKind.Kind.UNSIGNED_BYTE -> buffer.readByte().toUByte().toInt()
            ProtocolEnumKind.Kind.VAR_INT -> buffer.readVarInt()
        }
    }

    fun decodeVarInt() = buffer.readVarInt()

    override fun decodeSequentially(): Boolean = true

    @Suppress("UNCHECKED_CAST") // unchecked cast my beloved
    override fun <T> decodeSerializableValue(deserializer: DeserializationStrategy<T>): T {
        return if (deserializer.descriptor == varIntSerializer.descriptor) {
            VarInt(decodeVarInt()) as T
        } else if (deserializer is AnyToNbtSerializer<T>) {
            // ugly workaround for adventure nbt being awesome
            // reading a binary tag will consume all the bytes in the buffer
            // with no way to know how many were left over
            // so to find that out, we write that nbt tag again into a new byte array
            // and using its length move the reader index of the byte buffer
            // TODO: write my own nbt library

            val startIndex = buffer.readerIndex()

            BinaryTagIO.reader()
                .readNameless(
                    ByteBufInputStream(buffer),
                    BinaryTagIO.Compression.NONE
                )
                .also { nbt ->
                    ByteArrayOutputStream()
                        .also {
                            BinaryTagIO.writer()
                                .writeNameless(nbt, it, BinaryTagIO.Compression.NONE)
                        }
                        .size()
                        .let {
                            buffer.readerIndex(startIndex + it)
                        }
                }
                .let {
                    decodeFromNbt(deserializer.surrogate, it)
                }
        } else {
            super.decodeSerializableValue(deserializer)
        }
    }

    override fun beginStructure(descriptor: SerialDescriptor): CompositeDecoder {
        if (descriptor.kind == StructureKind.LIST) {
            return ByteBufCollectionDecoder(buffer, serializersModule)
        }

        return ByteBufObjectDecoder(buffer, serializersModule)
    }
}

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
    override val serializersModule: SerializersModule
) : AbstractByteBufDecoder() {
    private var index = 0

    override fun decodeElementIndex(descriptor: SerialDescriptor): Int {
        return index++
    }
}

fun <T> ByteBuf.decode(deserializer: DeserializationStrategy<T>): T {
    return ByteBufDecoder(this, EmptySerializersModule())
        .decodeSerializableValue(deserializer)
}

inline fun <reified T> ByteBuf.decode(): T = decode(serializer())

