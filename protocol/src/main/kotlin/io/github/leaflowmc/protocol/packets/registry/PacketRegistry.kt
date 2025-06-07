package io.github.leaflowmc.protocol.packets.registry

import io.github.leaflowmc.protocol.packets.ProtocolStage
import kotlinx.serialization.KSerializer

interface PacketRegistry<T> {
    operator fun get(id: Int, stage: ProtocolStage) : KSerializer<out T>?
}