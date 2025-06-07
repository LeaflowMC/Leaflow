package io.github.leaflowmc.protocol.packets.registry

import io.github.leaflowmc.protocol.packets.ClientPacket
import io.github.leaflowmc.protocol.packets.ProtocolStage
import io.github.leaflowmc.protocol.packets.handshake.ServerboundHandshakePacket
import kotlinx.serialization.KSerializer

object ClientPacketRegistry : PacketRegistry<ClientPacket> {
    val HANDSHAKE = createPacketInfo<ClientPacket> {
    }
    val STATUS = createPacketInfo<ClientPacket> {
    }
    val LOGIN = createPacketInfo<ClientPacket> {
    }
    val CONFIGURATION = createPacketInfo<ClientPacket> {
    }
    val PLAY = createPacketInfo<ClientPacket> {
    }

    override fun get(
        id: Int,
        stage: ProtocolStage
    ): KSerializer<out ClientPacket>? {
        return when (stage) {
            ProtocolStage.HANDSHAKE -> HANDSHAKE[id]
            ProtocolStage.STATUS -> STATUS[id]
            ProtocolStage.LOGIN -> LOGIN[id]
            ProtocolStage.CONFIGURATION -> CONFIGURATION[id]
            ProtocolStage.PLAY -> PLAY[id]
        }
    }
}