package io.github.leaflowmc.serialization

import io.github.leaflowmc.common.utils.*
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.SerializationStrategy
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.AbstractEncoder
import kotlinx.serialization.modules.EmptySerializersModule
import kotlinx.serialization.modules.SerializersModule
import java.io.OutputStream

@OptIn(ExperimentalSerializationApi::class)
class OutputStreamMinecraftEncoder(
    val stream: OutputStream,
    override val serializersModule: SerializersModule
) : AbstractEncoder() {
    override fun encodeByte(value: Byte) = stream.writeByte(value)
    override fun encodeBoolean(value: Boolean) = stream.writeBoolean(value)
    override fun encodeShort(value: Short) = stream.writeShort(value)
    override fun encodeInt(value: Int) = stream.writeInt(value)
    override fun encodeLong(value: Long) = stream.writeLong(value)
    override fun encodeFloat(value: Float) = stream.writeFloat(value)
    override fun encodeDouble(value: Double) = stream.writeDouble(value)
    override fun encodeChar(value: Char) = stream.writeByte(value.code.toByte())
    override fun encodeString(value: String) = stream.writePrefixedString(value)
    override fun encodeEnum(enumDescriptor: SerialDescriptor, index: Int) = stream.writeVarInt(index)
    fun encodeVarInt(int: Int) = stream.writeVarInt(int)

    override fun encodeNull() = encodeBoolean(false)
    override fun encodeNotNullMark() = encodeBoolean(true)

    override fun <T> encodeSerializableValue(serializer: SerializationStrategy<T>, value: T) {
        when (serializer.descriptor) {
            varIntSerializer.descriptor -> encodeVarInt(value as Int)

            else -> super.encodeSerializableValue(serializer, value)
        }
    }
}

fun OutputStream.minecraftEncoder(serializersModule: SerializersModule = EmptySerializersModule()): OutputStreamMinecraftEncoder {
    return OutputStreamMinecraftEncoder(this, serializersModule)
}