package io.github.leaflowmc.leaflow.serialization

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.json.JsonClassDiscriminator

private const val DEFAULT_DISCRIMINATOR = "type"

@ExperimentalSerializationApi
internal fun SerialDescriptor.classDiscriminator(): String {
    annotations.forEach {
        if (it is JsonClassDiscriminator) return it.discriminator
    }

    return DEFAULT_DISCRIMINATOR
}