package io.github.leaflowmc.leaflow.serialization.minecraft_format

import io.github.leaflowmc.leaflow.common.utils.writePrefixedString
import io.github.leaflowmc.leaflow.common.utils.writeVarInt
import io.netty.buffer.ByteBuf
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.KSerializer
import kotlinx.serialization.SerializationStrategy
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.AbstractEncoder
import kotlinx.serialization.modules.EmptySerializersModule
import kotlinx.serialization.modules.SerializersModule

@OptIn(ExperimentalSerializationApi::class)
class ByteBufMinecraftEncoder(
    val byteBuf: ByteBuf,
    override val serializersModule: SerializersModule
) : AbstractEncoder() {
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
        byteBuf.writePrefixedString(value)
    }

    override fun encodeEnum(enumDescriptor: SerialDescriptor, index: Int) {
        byteBuf.writeVarInt(index)
    }

    fun encodeVarInt(int: Int) = byteBuf.writeVarInt(int)

    override fun encodeNull() = encodeBoolean(false)
    override fun encodeNotNullMark() = encodeBoolean(true)

    override fun <T> encodeSerializableValue(serializer: SerializationStrategy<T>, value: T) {
        when (serializer.descriptor) {
            varIntSerializer.descriptor -> encodeVarInt(value as Int)

            else -> super.encodeSerializableValue(serializer, value)
        }
    }
}

fun <T> ByteBuf.encodePacket(serializer: KSerializer<T>, value: T) {
    ByteBufMinecraftEncoder(this, EmptySerializersModule())
        .encodeSerializableValue(serializer, value)
}