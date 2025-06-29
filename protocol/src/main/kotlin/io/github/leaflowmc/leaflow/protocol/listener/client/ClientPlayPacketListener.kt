package io.github.leaflowmc.leaflow.protocol.listener.client

import io.github.leaflowmc.leaflow.protocol.packets.play.clientbound.ClientboundAbilitiesPacket
import io.github.leaflowmc.leaflow.protocol.packets.play.clientbound.ClientboundChangeDifficultyPacket
import io.github.leaflowmc.leaflow.protocol.packets.play.clientbound.ClientboundLoginPacket
import io.github.leaflowmc.leaflow.protocol.packets.play.clientbound.ClientboundServerDataPacket
import io.github.leaflowmc.leaflow.protocol.packets.play.clientbound.ClientboundSetDefaultSpawnPositionPacket
import io.github.leaflowmc.leaflow.protocol.packets.play.clientbound.ClientboundSetHeldItemPacket
import io.github.leaflowmc.leaflow.protocol.packets.play.clientbound.ClientboundSetTitleTextPacket
import io.github.leaflowmc.leaflow.protocol.packets.play.clientbound.ClientboundStartConfigurationPacket
import io.github.leaflowmc.leaflow.protocol.packets.play.clientbound.ClientboundUpdateTimePacket
import io.github.leaflowmc.leaflow.protocol.packets.play.clientbound.ClientboundGameEventPacket
import io.github.leaflowmc.leaflow.protocol.packets.play.clientbound.ClientboundSetTickingStatePacket
import io.github.leaflowmc.leaflow.protocol.packets.play.clientbound.ClientboundStepTickPacket
import io.github.leaflowmc.leaflow.protocol.packets.play.clientbound.ClientboundTeleportPacket

interface ClientPlayPacketListener : ClientPingPacketListener, ClientCommonPacketListener {
    fun login(packet: ClientboundLoginPacket)
    fun abilities(packet: ClientboundAbilitiesPacket)
    fun setHeldItem(packet: ClientboundSetHeldItemPacket)
    fun serverData(packet: ClientboundServerDataPacket)
    fun updateTime(packet: ClientboundUpdateTimePacket)
    fun setTitleText(packet: ClientboundSetTitleTextPacket)
    fun startConfiguration(packet: ClientboundStartConfigurationPacket)
    fun setDefaultSpawnPosition(packet: ClientboundSetDefaultSpawnPositionPacket)
    fun gameEvent(packet: ClientboundGameEventPacket)
    fun setTickingState(packet: ClientboundSetTickingStatePacket)
    fun stepTick(packet: ClientboundStepTickPacket)
    fun teleport(packet: ClientboundTeleportPacket)
    fun changeDifficulty(packet: ClientboundChangeDifficultyPacket)
}