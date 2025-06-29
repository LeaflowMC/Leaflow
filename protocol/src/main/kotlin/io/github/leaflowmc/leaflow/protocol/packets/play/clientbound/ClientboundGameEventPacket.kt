package io.github.leaflowmc.leaflow.protocol.packets.play.clientbound

import io.github.leaflowmc.leaflow.protocol.listener.client.ClientPlayPacketListener
import io.github.leaflowmc.leaflow.protocol.packets.ClientPacket
import io.github.leaflowmc.leaflow.serialization.annotations.ProtocolEnumKind
import kotlinx.serialization.Serializable

/**
 * @property event One of [GameEvent]
 * @property value depends on [event]
 */
@Serializable
data class ClientboundGameEventPacket(
    @ProtocolEnumKind(ProtocolEnumKind.Kind.UNSIGNED_BYTE)
    val event: GameEvent,
    val value: Float
) : ClientPacket<ClientPlayPacketListener> {
    override fun handle(listener: ClientPlayPacketListener) {
        listener.gameEvent(this)
    }

    enum class GameEvent {
        /**
         * Displays message 'block.minecraft.spawn.not_valid'
         * (You have no home bed or charged respawn anchor, or it was obstructed) to the player.
         */
        NO_RESPAWN_BLOCK_AVAILABLE,
        BEGIN_RAINING,
        END_RAINING,

        /**
         * Value is ordinal of [io.github.leaflowmc.leaflow.protocol.types.GameMode] enum
         */
        CHANGE_GAME_MODE,

        /**
         * `0`: respawn player
         * `1`: roll credits and respawn player
         */
        WIN_GAME,
        DEMO_EVENT,
        ARROW_HIT_PLAYER,

        /**
         * Value is rain level from `0f` to `1f`
         */
        RAIN_LEVEL_CHANGE,

        /**
         * Value is thunder level from `0f` to `1f`
         */
        THUNDER_LEVEL_CHANGE,
        PLAY_PUFFERFISH_STING_SOUND,
        PLAY_ELDER_GUARDIAN_EFFECT,

        /**
         * `0`: disable
         * `1`: enable
         */
        ENABLE_RESPAWN_SCREEN,

        /**
         * `0`: disable
         * `1`: enable limited crafting
         */
        LIMITED_CRAFTING,
        START_WAITING_FOR_CHUNKS;
    }
}