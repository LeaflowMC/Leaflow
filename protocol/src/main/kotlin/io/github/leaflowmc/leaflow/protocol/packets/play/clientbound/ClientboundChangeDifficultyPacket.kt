package io.github.leaflowmc.leaflow.protocol.packets.play.clientbound

import io.github.leaflowmc.leaflow.protocol.listener.client.ClientPlayPacketListener
import io.github.leaflowmc.leaflow.protocol.packets.ClientPacket
import io.github.leaflowmc.leaflow.protocol.types.Difficulty
import io.github.leaflowmc.leaflow.serialization.annotations.ProtocolEnumKind
import kotlinx.serialization.Serializable

@Serializable
data class ClientboundChangeDifficultyPacket(
    @ProtocolEnumKind(ProtocolEnumKind.Kind.UNSIGNED_BYTE)
    val difficulty: Difficulty,
    val locked: Boolean
) : ClientPacket<ClientPlayPacketListener> {
    override fun handle(listener: ClientPlayPacketListener) {
        listener.changeDifficulty(this)
    }
}