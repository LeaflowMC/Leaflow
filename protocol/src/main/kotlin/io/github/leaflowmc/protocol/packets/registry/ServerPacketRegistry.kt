package io.github.leaflowmc.protocol.packets.registry

import io.github.leaflowmc.protocol.packets.ProtocolStage
import io.github.leaflowmc.protocol.packets.ServerPacket
import io.github.leaflowmc.protocol.packets.handshake.ServerboundHandshakePacket
import kotlinx.serialization.KSerializer

object ServerPacketRegistry {
    val HANDSHAKE = createServerPacketInfo<ServerPacket> {
        addPacket(ServerboundHandshakePacket.serializer())
    }
    val STATUS = createServerPacketInfo<ServerPacket> {
    }
    val LOGIN = createServerPacketInfo<ServerPacket> {
    }
    val CONFIGURATION = createServerPacketInfo<ServerPacket> {
    }
    val PLAY = createServerPacketInfo<ServerPacket> {
    }

    operator fun get(
        id: Int,
        stage: ProtocolStage
    ): KSerializer<out ServerPacket>? {
        return when (stage) {
            ProtocolStage.HANDSHAKE -> HANDSHAKE[id]
            ProtocolStage.STATUS -> STATUS[id]
            ProtocolStage.LOGIN -> LOGIN[id]
            ProtocolStage.CONFIGURATION -> CONFIGURATION[id]
            ProtocolStage.PLAY -> PLAY[id]
        }
    }
}