package io.github.leaflowmc.leaflow.serialization.minecraft_format

import io.github.leaflowmc.leaflow.common.utils.VarInt
import io.github.leaflowmc.leaflow.common.utils.readPrefixedString
import io.github.leaflowmc.leaflow.common.utils.readVarInt
import io.github.leaflowmc.leaflow.serialization.annotations.AsNbt
import io.github.leaflowmc.leaflow.serialization.annotations.NotLengthPrefixed
import io.github.leaflowmc.leaflow.serialization.annotations.ProtocolEnumKind
import io.github.leaflowmc.leaflow.serialization.annotations.protocolEnumKind
import io.github.leaflowmc.leaflow.serialization.nbt.decodeFromNbt
import io.netty.buffer.ByteBuf
import io.netty.buffer.ByteBufInputStream
import kotlinx.serialization.DeserializationStrategy
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.descriptors.StructureKind
import kotlinx.serialization.encoding.CompositeDecoder
import kotlinx.serialization.encoding.Decoder
import net.kyori.adventure.nbt.BinaryTagIO
import java.io.ByteArrayOutputStream
import java.nio.charset.StandardCharsets

@OptIn(ExperimentalSerializationApi::class)
abstract class AbstractByteBufDecoder : Decoder, CompositeDecoder {
    protected abstract val buffer: ByteBuf

    private var lastElementAnnotations: Collection<Annotation>? = null

    private fun pushAnnotations(descriptor: SerialDescriptor, index: Int) {
        lastElementAnnotations = descriptor.getElementAnnotations(index)
    }

    final override fun decodeByte(): Byte = buffer.readByte()
    final override fun decodeBoolean(): Boolean = buffer.readBoolean()
    final override fun decodeShort(): Short = buffer.readShort()
    final override fun decodeInt(): Int = buffer.readInt()
    final override fun decodeLong(): Long = buffer.readLong()
    final override fun decodeFloat(): Float = buffer.readFloat()
    final override fun decodeDouble(): Double = buffer.readDouble()
    final override fun decodeChar(): Char = buffer.readChar()
    final override fun decodeNotNullMark(): Boolean = decodeBoolean()
    override fun decodeCollectionSize(descriptor: SerialDescriptor): Int = decodeVarInt()

    final override fun decodeString(): String {
        val nonLengthPrefixed = lastElementAnnotations
            ?.firstNotNullOfOrNull { it as? NotLengthPrefixed }

        return if (nonLengthPrefixed == null) {
            buffer.readPrefixedString()
        } else if (nonLengthPrefixed.length >= 0) {
            buffer.readString(nonLengthPrefixed.length, StandardCharsets.UTF_8)
        } else {
            val bytes = ByteArray(buffer.readableBytes())
            buffer.readBytes(bytes)

            bytes.toString(StandardCharsets.UTF_8)
        }
    }

    final override fun decodeEnum(enumDescriptor: SerialDescriptor): Int {
        val annotation = lastElementAnnotations
            ?.firstNotNullOfOrNull { it as ProtocolEnumKind }

        return when(annotation?.kind) {
            ProtocolEnumKind.Kind.UNSIGNED_BYTE -> buffer.readByte().toUByte().toInt()
            ProtocolEnumKind.Kind.VAR_INT,
            null -> buffer.readVarInt()
        }
    }

    fun decodeVarInt() = buffer.readVarInt()

    override fun decodeSequentially(): Boolean = true

    @Suppress("UNCHECKED_CAST") // unchecked cast my beloved
    override fun <T> decodeSerializableValue(deserializer: DeserializationStrategy<T>): T {
        return if (deserializer.descriptor == varIntSerializer.descriptor) {
            VarInt(decodeVarInt()) as T
        } else if (lastElementAnnotations?.any { it is AsNbt } == true) {
            // ugly workaround for adventure nbt being awesome
            // reading a binary tag will consume all the bytes in the buffer
            // with no way to know how many were left over
            // so to find that out, we write that nbt tag again into a new byte array
            // and using its length move the reader index of the byte buffer

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
                    decodeFromNbt(deserializer, it)
                }
        } else {
            super.decodeSerializableValue(deserializer)
        }
    }

    override fun beginStructure(descriptor: SerialDescriptor): CompositeDecoder {
        if (descriptor.kind == StructureKind.LIST) {
            return ByteBufCollectionDecoder(buffer, serializersModule, lastElementAnnotations)
        }

        return ByteBufObjectDecoder(buffer, serializersModule)
    }

    override fun <T> decodeSerializableElement(
        descriptor: SerialDescriptor,
        index: Int,
        deserializer: DeserializationStrategy<T>,
        previousValue: T?
    ): T {
        pushAnnotations(descriptor, index)

        return decodeSerializableValue(deserializer)
    }

    override fun endStructure(descriptor: SerialDescriptor) {
    }

    override fun decodeBooleanElement(
        descriptor: SerialDescriptor,
        index: Int
    ): Boolean = decodeBoolean()

    override fun decodeByteElement(
        descriptor: SerialDescriptor,
        index: Int
    ): Byte = decodeByte()

    override fun decodeCharElement(
        descriptor: SerialDescriptor,
        index: Int
    ): Char = decodeChar()

    override fun decodeShortElement(
        descriptor: SerialDescriptor,
        index: Int
    ): Short = decodeShort()

    override fun decodeIntElement(
        descriptor: SerialDescriptor,
        index: Int
    ): Int = decodeInt()

    override fun decodeLongElement(
        descriptor: SerialDescriptor,
        index: Int
    ): Long = decodeLong()

    override fun decodeFloatElement(
        descriptor: SerialDescriptor,
        index: Int
    ): Float = decodeFloat()

    override fun decodeDoubleElement(
        descriptor: SerialDescriptor,
        index: Int
    ): Double = decodeDouble()

    override fun decodeStringElement(
        descriptor: SerialDescriptor,
        index: Int
    ): String {
        pushAnnotations(descriptor, index)

        return decodeString()
    }

    override fun decodeInlineElement(
        descriptor: SerialDescriptor,
        index: Int
    ): Decoder {
        return decodeInline(descriptor.getElementDescriptor(index))
    }

    @ExperimentalSerializationApi
    override fun <T : Any> decodeNullableSerializableElement(
        descriptor: SerialDescriptor,
        index: Int,
        deserializer: DeserializationStrategy<T?>,
        previousValue: T?
    ): T? {
        val isNullabilitySupported = deserializer.descriptor.isNullable
        return if (isNullabilitySupported || decodeNotNullMark()) {
            decodeSerializableValue(deserializer)
        } else decodeNull()
    }

    @ExperimentalSerializationApi
    override fun decodeNull(): Nothing? = null

    override fun decodeInline(descriptor: SerialDescriptor): Decoder = this
}