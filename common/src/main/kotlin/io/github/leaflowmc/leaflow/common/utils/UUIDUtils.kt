package io.github.leaflowmc.leaflow.common.utils

import java.nio.charset.StandardCharsets
import java.util.UUID

object UUIDUtils {
    fun createOffline(name: String): UUID {
        return "OfflinePlayer:$name"
            .toByteArray(StandardCharsets.UTF_8)
            .let(UUID::nameUUIDFromBytes)
    }
}