package io.github.leaflowmc.leaflow.serialization.minecraft_format

import io.github.leaflowmc.leaflow.common.utils.VarInt
import io.github.leaflowmc.leaflow.common.utils.writePrefixedString
import io.github.leaflowmc.leaflow.common.utils.writeString
import io.github.leaflowmc.leaflow.common.utils.writeVarInt
import io.github.leaflowmc.leaflow.serialization.annotations.AsNbt
import io.github.leaflowmc.leaflow.serialization.annotations.NotLengthPrefixed
import io.github.leaflowmc.leaflow.serialization.annotations.ProtocolEnumKind
import io.github.leaflowmc.leaflow.serialization.annotations.protocolEnumKind
import io.github.leaflowmc.leaflow.serialization.nbt.encodeToNbt
import io.netty.buffer.ByteBuf
import io.netty.buffer.ByteBufOutputStream
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.SerializationStrategy
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.AbstractEncoder
import kotlinx.serialization.encoding.CompositeEncoder
import kotlinx.serialization.modules.EmptySerializersModule
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.serializer
import net.kyori.adventure.nbt.BinaryTagIO
import net.kyori.adventure.nbt.CompoundBinaryTag
import java.io.OutputStream

@OptIn(ExperimentalSerializationApi::class)
class ByteBufEncoder(
    val byteBuf: ByteBuf,
    override val serializersModule: SerializersModule
) : AbstractEncoder() {
    private var lastElementAnnotations: Collection<Annotation>? = null

    override fun encodeByte(value: Byte) {
        byteBuf.writeByte(value.toInt())
    }

    override fun encodeBoolean(value: Boolean) {
        byteBuf.writeBoolean(value)
    }

    override fun encodeShort(value: Short) {
        byteBuf.writeShort(value.toInt())
    }

    override fun encodeInt(value: Int) {
        byteBuf.writeInt(value)
    }

    override fun encodeLong(value: Long) {
        byteBuf.writeLong(value)
    }

    override fun encodeFloat(value: Float) {
        byteBuf.writeFloat(value)
    }

    override fun encodeDouble(value: Double) {
        byteBuf.writeDouble(value)
    }

    override fun encodeChar(value: Char) {
        byteBuf.writeChar(value.code)
    }

    override fun encodeString(value: String) {
        val notLengthPrefixed = lastElementAnnotations
            ?.firstNotNullOfOrNull { it as? NotLengthPrefixed }

        if (notLengthPrefixed == null) {
            byteBuf.writePrefixedString(value)
        } else {
            byteBuf.writeString(value)
        }
    }

    override fun encodeEnum(enumDescriptor: SerialDescriptor, index: Int) {
        val annotation = lastElementAnnotations
            ?.firstNotNullOfOrNull { it as ProtocolEnumKind }

        when(annotation?.kind) {
            ProtocolEnumKind.Kind.UNSIGNED_BYTE -> byteBuf.writeByte(index)
            ProtocolEnumKind.Kind.VAR_INT,
            null -> byteBuf.writeVarInt(index)
            ProtocolEnumKind.Kind.STRING -> {
                val str = enumDescriptor.getElementName(index)
                byteBuf.writePrefixedString(str.lowercase())
            }
        }
    }

    fun encodeVarInt(int: Int) = byteBuf.writeVarInt(int)

    override fun encodeNull() = encodeBoolean(false)
    override fun encodeNotNullMark() = encodeBoolean(true)

    override fun beginCollection(descriptor: SerialDescriptor, collectionSize: Int): CompositeEncoder {
        if (lastElementAnnotations?.any { it is NotLengthPrefixed } != true) {
            encodeVarInt(collectionSize)
        }

        return this
    }

    override fun <T> encodeSerializableValue(serializer: SerializationStrategy<T>, value: T) {
        if (serializer.descriptor == varIntSerializer.descriptor) {
            encodeVarInt((value as VarInt).value)
        } else if (lastElementAnnotations?.any { it is AsNbt } == true) {
            BinaryTagIO.writer()
                .writeNameless(
                    encodeToNbt(serializer, value) as CompoundBinaryTag,
                    ByteBufOutputStream(byteBuf) as OutputStream,
                    BinaryTagIO.Compression.NONE
                )
        } else {
            super.encodeSerializableValue(serializer, value)
        }
    }

    override fun encodeElement(descriptor: SerialDescriptor, index: Int): Boolean {
        lastElementAnnotations = descriptor.getElementAnnotations(index)

        return true
    }
}

fun <T> ByteBuf.encode(serializer: SerializationStrategy<T>, value: T): ByteBuf {
    ByteBufEncoder(this, EmptySerializersModule())
        .encodeSerializableValue(serializer, value)

    return this
}

inline fun <reified T> ByteBuf.encode(value: T): ByteBuf {
    encode(serializer(), value)
    return this
}

