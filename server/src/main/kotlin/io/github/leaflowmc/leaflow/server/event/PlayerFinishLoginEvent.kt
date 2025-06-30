package io.github.leaflowmc.leaflow.server.event

import io.github.leaflowmc.leaflow.server.player.Player
import io.github.leaflowmc.leaflow.server.player.PlayerConnection

data class PlayerFinishLoginEvent(override val connection: PlayerConnection, val player: Player) : Event