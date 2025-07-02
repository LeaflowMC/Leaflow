package io.github.leaflowmc.leaflow.common.types

import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder

object IdentifierSerializer : KSerializer<Identifier> {
    override val descriptor: SerialDescriptor = PrimitiveSerialDescriptor("Identifier", PrimitiveKind.STRING)

    override fun serialize(
        encoder: Encoder,
        value: Identifier
    ) {
        encoder.encodeString(value.asString())
    }

    override fun deserialize(decoder: Decoder): Identifier {
        return Identifier.of(decoder.decodeString())
    }
}