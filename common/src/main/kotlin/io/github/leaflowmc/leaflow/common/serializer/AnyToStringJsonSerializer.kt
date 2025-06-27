package io.github.leaflowmc.leaflow.common.serializer

import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import kotlinx.serialization.json.Json

open class AnyToStringJsonSerializer<T>(val actual: KSerializer<T>) : KSerializer<T> {
    final override val descriptor: SerialDescriptor = PrimitiveSerialDescriptor("AnyToString", PrimitiveKind.STRING)

    final override fun serialize(encoder: Encoder, value: T) {
        Json.encodeToString(actual, value)
            .let(encoder::encodeString)
    }

    final override fun deserialize(decoder: Decoder): T {
        return decoder.decodeString()
            .let {
                Json.decodeFromString(actual, it)
            }
    }

}