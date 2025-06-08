package io.github.leaflowmc.serialization

import io.github.leaflowmc.common.utils.VarInt
import io.github.leaflowmc.common.utils.readBoolean
import io.github.leaflowmc.common.utils.readByte
import io.github.leaflowmc.common.utils.readDouble
import io.github.leaflowmc.common.utils.readFloat
import io.github.leaflowmc.common.utils.readInt
import io.github.leaflowmc.common.utils.readLong
import io.github.leaflowmc.common.utils.readPrefixedString
import io.github.leaflowmc.common.utils.readShort
import io.github.leaflowmc.common.utils.readVarInt
import kotlinx.serialization.DeserializationStrategy
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.builtins.serializer
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.AbstractDecoder
import kotlinx.serialization.encoding.CompositeDecoder.Companion.DECODE_DONE
import kotlinx.serialization.modules.EmptySerializersModule
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.serializer
import java.io.InputStream

val varIntSerializer = serializer<VarInt>()

@OptIn(ExperimentalSerializationApi::class)
class InputStreamMinecraftDecoder(
    val stream: InputStream,
    override val serializersModule: SerializersModule
) : AbstractDecoder() {
    var counter = 0

    override fun decodeByte(): Byte = stream.readByte()
    override fun decodeBoolean(): Boolean = stream.readBoolean()
    override fun decodeShort(): Short = stream.readShort()
    override fun decodeInt(): Int = stream.readInt()
    override fun decodeLong(): Long = stream.readLong()
    override fun decodeFloat(): Float = stream.readFloat()
    override fun decodeDouble(): Double = stream.readDouble()
    override fun decodeChar(): Char = stream.readByte().toInt().toChar()
    override fun decodeString(): String = stream.readPrefixedString()
    override fun decodeEnum(enumDescriptor: SerialDescriptor): Int = stream.readVarInt()
    fun decodeVarInt() = stream.readVarInt()

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

fun InputStream.minecraftDecoder(serializersModule: SerializersModule = EmptySerializersModule()): InputStreamMinecraftDecoder {
    return InputStreamMinecraftDecoder(this, serializersModule)
}
