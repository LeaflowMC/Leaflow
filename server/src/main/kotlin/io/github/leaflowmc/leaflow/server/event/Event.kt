package io.github.leaflowmc.leaflow.server.event

import io.github.leaflowmc.leaflow.server.player.Player
import io.github.leaflowmc.leaflow.server.player.PlayerConnection
import kotlinx.coroutines.Deferred

interface Event {
    val connection: PlayerConnection
}

inline val Event.player: Deferred<Player>
    get() = connection.player