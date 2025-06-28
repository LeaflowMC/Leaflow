package io.github.leaflowmc.leaflow.protocol.listener.client

import io.github.leaflowmc.leaflow.protocol.packets.configuration.ClientboundFinishConfigurationPacket
import io.github.leaflowmc.leaflow.protocol.packets.configuration.ClientboundKnownPacksPacket
import io.github.leaflowmc.leaflow.protocol.packets.configuration.ClientboundResetChatPacket

interface ClientConfigurationPacketListener : ClientPingPacketListener, ClientCommonPacketListener {
    fun resetChat(packet: ClientboundResetChatPacket)
    fun knownPacks(packet: ClientboundKnownPacksPacket)
    fun finishConfiguration(packet: ClientboundFinishConfigurationPacket)
}