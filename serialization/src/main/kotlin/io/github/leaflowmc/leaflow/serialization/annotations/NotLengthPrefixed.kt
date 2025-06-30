package io.github.leaflowmc.leaflow.serialization.annotations

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.SerialInfo

/**
 * Used to tell the [io.github.leaflowmc.leaflow.serialization.minecraft_format.ByteBufEncoder]
 * and [io.github.leaflowmc.leaflow.serialization.minecraft_format.ByteBufDecoder]
 * that this collection (or string) is not length prefixed
 *
 * - when [length] is less than 0, the decoder will try to read the whole buffer into this collection
 * - otherwise, decoder will read exactly [length] elements
 */
@OptIn(ExperimentalSerializationApi::class)
@SerialInfo
@Target(AnnotationTarget.PROPERTY)
annotation class NotLengthPrefixed(val length: Int = -1)
