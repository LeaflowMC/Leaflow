package io.github.leaflowmc.serialization

import io.github.leaflowmc.common.utils.readBoolean
import io.github.leaflowmc.common.utils.readByte
import io.github.leaflowmc.common.utils.readDouble
import io.github.leaflowmc.common.utils.readFloat
import io.github.leaflowmc.common.utils.readLong
import io.github.leaflowmc.common.utils.readPrefixedString
import io.github.leaflowmc.common.utils.readShort
import io.github.leaflowmc.common.utils.readVarInt
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.modules.EmptySerializersModule
import kotlinx.serialization.modules.SerializersModule
import java.io.InputStream

class InputStreamMinecraftDecoder(
    val stream: InputStream,
    serializersModule: SerializersModule
) : MinecraftProtocolDecoder(serializersModule) {
    override fun decodeByte(): Byte = stream.readByte()
    override fun decodeBoolean(): Boolean = stream.readBoolean()
    override fun decodeShort(): Short = stream.readShort()
    override fun decodeInt(): Int = stream.readVarInt() // TODO support annotations
    override fun decodeLong(): Long = stream.readLong()
    override fun decodeFloat(): Float = stream.readFloat()
    override fun decodeDouble(): Double = stream.readDouble()
    override fun decodeChar(): Char = stream.readShort().toChar()
    override fun decodeString(): String = stream.readPrefixedString() // TODO support annotations
    override fun decodeEnum(enumDescriptor: SerialDescriptor): Int = stream.readVarInt() // TODO iirc enums can be a byte. support annotations
}

fun InputStream.minecraftDecoder(serializersModule: SerializersModule = EmptySerializersModule()): InputStreamMinecraftDecoder {
    return InputStreamMinecraftDecoder(this, serializersModule)
}