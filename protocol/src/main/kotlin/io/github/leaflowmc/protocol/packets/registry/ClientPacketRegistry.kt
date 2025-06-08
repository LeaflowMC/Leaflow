package io.github.leaflowmc.protocol.packets.registry

import io.github.leaflowmc.protocol.packets.ClientPacket
import io.github.leaflowmc.protocol.packets.ProtocolStage
import kotlin.reflect.KClass

object ClientPacketRegistry {
    val STATUS = createClientPacketInfo<ClientPacket> {
    }
    val LOGIN = createClientPacketInfo<ClientPacket> {
    }
    val CONFIGURATION = createClientPacketInfo<ClientPacket> {
    }
    val PLAY = createClientPacketInfo<ClientPacket> {
    }

    operator fun get(
        clazz: KClass<ClientPacket>,
        stage: ProtocolStage
    ): Int? {
        return when (stage) {
            ProtocolStage.HANDSHAKE -> null
            ProtocolStage.STATUS -> STATUS[clazz]
            ProtocolStage.LOGIN -> LOGIN[clazz]
            ProtocolStage.CONFIGURATION -> CONFIGURATION[clazz]
            ProtocolStage.PLAY -> PLAY[clazz]
        }
    }
}