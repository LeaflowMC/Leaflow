package io.github.leaflowmc.leaflow.protocol.listener.server

import io.github.leaflowmc.leaflow.protocol.packets.configuration.ServerboundAcknowledgeFinishConfigurationPacket
import io.github.leaflowmc.leaflow.protocol.packets.configuration.ServerboundClientInfoPacket
import io.github.leaflowmc.leaflow.protocol.packets.configuration.ServerboundKnownPacksPacket

interface ServerConfigurationPacketListener : ServerCommonPacketListener {
    fun clientInfo(packet: ServerboundClientInfoPacket)
    fun knownPacks(packet: ServerboundKnownPacksPacket)
    fun acknowledgeFinishConfiguration(packet: ServerboundAcknowledgeFinishConfigurationPacket)
}