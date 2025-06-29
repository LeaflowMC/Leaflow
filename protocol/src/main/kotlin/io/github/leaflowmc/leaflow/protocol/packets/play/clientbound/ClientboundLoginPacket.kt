package io.github.leaflowmc.leaflow.protocol.packets.play.clientbound

import io.github.leaflowmc.leaflow.common.utils.VarInt
import io.github.leaflowmc.leaflow.protocol.listener.client.ClientPlayPacketListener
import io.github.leaflowmc.leaflow.protocol.packets.ClientPacket
import io.github.leaflowmc.leaflow.protocol.types.DeathLocation
import io.github.leaflowmc.leaflow.protocol.types.GameMode
import io.github.leaflowmc.leaflow.serialization.annotations.ProtocolEnumKind
import kotlinx.serialization.Serializable

@Serializable
data class ClientboundLoginPacket(
    val entityId: Int,
    val isHardcode: Boolean,
    val dimensionNames: Collection<String>,
    val maxPlayers: VarInt,
    val viewDistance: VarInt,
    val simulationDistance: VarInt,
    val reducedDebugInfo: Boolean,
    val enableRespawnScreen: Boolean,
    val doLimitedCrafting: Boolean,
    val dimensionType: VarInt,
    val dimensionName: String,
    val hashedSeed: Long,
    @ProtocolEnumKind(ProtocolEnumKind.Kind.UNSIGNED_BYTE)
    val gameMode: GameMode,
    val previousGameModeByte: Byte,
    val isDebugWorld: Boolean,
    val isFlat: Boolean,
    val deathLocation: DeathLocation?,
    val portalCooldown: VarInt,
    val seaLevel: VarInt,
    val enforcesSecureChat: Boolean
) : ClientPacket<ClientPlayPacketListener> {
    init {
        require(viewDistance.value in 2..32) { "viewDistance must be between 2 and 32 (inclusive)" }
        require(simulationDistance.value in 2..32) { "simulationDistance must be between 2 and 32 (inclusive)" }
    }

    val previousGameMode: GameMode?
        get() {
            val i = previousGameModeByte.toInt()

            return if (i == -1) {
                null
            } else {
                GameMode.entries[i]
            }
        }

    override fun handle(listener: ClientPlayPacketListener) {
        listener.login(this)
    }
}