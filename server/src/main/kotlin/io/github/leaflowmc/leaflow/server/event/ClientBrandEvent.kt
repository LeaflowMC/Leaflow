package io.github.leaflowmc.leaflow.server.event

import io.github.leaflowmc.leaflow.server.player.PlayerConnection

data class ClientBrandEvent(override val connection: PlayerConnection, val brand: String) : Event