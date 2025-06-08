package io.github.leaflowmc.serialization

import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.serializer

class JsonAsStringSerializer : KSerializer<Any> {
    private val delegateSerializer = serializer<String>()

    override val descriptor: SerialDescriptor = SerialDescriptor(
        JsonAsStringSerializer::class.qualifiedName!!,
        delegateSerializer.descriptor
    )

    override fun serialize(encoder: Encoder, value: Any) {
        delegateSerializer.serialize(
            encoder,
            Json.encodeToString(value)
        )
    }

    override fun deserialize(decoder: Decoder): JsonObject = Json.decodeFromString(delegateSerializer.deserialize(decoder))
}