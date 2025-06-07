package io.github.leaflowmc.common.utils

import kotlinx.serialization.Serializable

@Serializable
@JvmInline
value class VarInt(val value: Int)