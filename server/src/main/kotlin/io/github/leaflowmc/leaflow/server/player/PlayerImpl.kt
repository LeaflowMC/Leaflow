package io.github.leaflowmc.leaflow.server.player

import io.github.leaflowmc.leaflow.common.GameProfile

open class PlayerImpl(
    override val connection: PlayerConnection,
    override val profile: GameProfile

) : Player {
}