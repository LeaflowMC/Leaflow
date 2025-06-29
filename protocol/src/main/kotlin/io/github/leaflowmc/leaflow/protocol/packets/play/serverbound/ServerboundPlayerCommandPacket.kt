package io.github.leaflowmc.leaflow.protocol.packets.play.serverbound

import io.github.leaflowmc.leaflow.common.utils.VarInt
import io.github.leaflowmc.leaflow.protocol.listener.server.ServerPlayPacketListener
import io.github.leaflowmc.leaflow.protocol.packets.ServerPacket
import kotlinx.serialization.Serializable

/**
 * @property jumpBoost only used when [action] is [Action.START_JUMP_WITH_HORSE],
 * from 0 to 100
 */
@Serializable
data class ServerboundPlayerCommandPacket(
    val playerId: VarInt,
    val action: Action,
    val jumpBoost: VarInt
) : ServerPacket<ServerPlayPacketListener> {
    override fun handle(listener: ServerPlayPacketListener) {
        listener.playerCommand(this)
    }

    enum class Action {
        LEAVE_BED,
        START_SPRINTING,
        START_JUMP_WITH_HORSE,
        STOP_JUMP_WITH_HORSE,
        OPEN_VEHICLE_INVENTORY,
        START_FLYING_WITH_ELYTRA;
    }
}