package io.github.leaflowmc.leaflow.common.serializer

import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import java.util.*
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid
import kotlin.uuid.toJavaUuid

@Serializable
private class UUIDAsLongsSurrogate(val mostSignificantBits: Long, val leastSignificantBits: Long) {
    fun toUUID(): UUID {
        return UUID(mostSignificantBits, leastSignificantBits)
    }
}

private fun UUID.toSurrogate(): UUIDAsLongsSurrogate {
    return UUIDAsLongsSurrogate(mostSignificantBits, leastSignificantBits)
}

object UUIDAsLongsSerializer : KSerializer<UUID> {
    override val descriptor: SerialDescriptor = SerialDescriptor("java.util.UUID", UUIDAsLongsSurrogate.serializer().descriptor)

    override fun serialize(encoder: Encoder, value: UUID) {
        encoder.encodeSerializableValue(
            UUIDAsLongsSurrogate.serializer(),
            value.toSurrogate()
        )
    }

    override fun deserialize(decoder: Decoder): UUID {
        return decoder.decodeSerializableValue(
            UUIDAsLongsSurrogate.serializer()
        ).toUUID()
    }
}

object UUIDAsStringSerializer : KSerializer<UUID> {
    override val descriptor: SerialDescriptor = PrimitiveSerialDescriptor("UUID", PrimitiveKind.STRING)

    override fun serialize(encoder: Encoder, value: UUID) {
        encoder.encodeString(value.toString())
    }

    @OptIn(ExperimentalUuidApi::class)
    override fun deserialize(decoder: Decoder): UUID {
        return Uuid.parse(decoder.decodeString()).toJavaUuid()
    }

}

typealias UUIDAsLongs = @Serializable(UUIDAsLongsSerializer::class) UUID
