package io.github.leaflowmc.leaflow.serialization.nbt

import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder

open class AnyToNbtSerializer<T>(val surrogate: KSerializer<T>) : KSerializer<T> {
    final override val descriptor: SerialDescriptor =
        SerialDescriptor("net.kyori.adventure.nbt.BinaryTag", surrogate.descriptor)

    override fun deserialize(decoder: Decoder): T {
        throw IllegalStateException("dummy serializer. do not deserialize")
    }

    override fun serialize(encoder: Encoder, value: T) {
        throw IllegalStateException("dummy serializer. do not serialize")
    }
}