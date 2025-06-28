package io.github.leaflowmc.leaflow.protocol.types

import kotlinx.serialization.Serializable

@Serializable
data class DataPack(val namespace: String, val id: String, val version: String)