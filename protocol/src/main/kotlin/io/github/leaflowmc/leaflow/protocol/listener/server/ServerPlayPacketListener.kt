package io.github.leaflowmc.leaflow.protocol.listener.server

import io.github.leaflowmc.leaflow.protocol.packets.play.serverbound.ServerboundAcknowledgeConfigurationPacket
import io.github.leaflowmc.leaflow.protocol.packets.play.serverbound.ServerboundClientTickEndPacket
import io.github.leaflowmc.leaflow.protocol.packets.play.serverbound.ServerboundConfirmTeleportationPacket
import io.github.leaflowmc.leaflow.protocol.packets.play.serverbound.ServerboundMoveVehiclePacket
import io.github.leaflowmc.leaflow.protocol.packets.play.serverbound.ServerboundPaddleBoatPacket
import io.github.leaflowmc.leaflow.protocol.packets.play.serverbound.ServerboundPlayerCommandPacket
import io.github.leaflowmc.leaflow.protocol.packets.play.serverbound.ServerboundPlayerInputPacket
import io.github.leaflowmc.leaflow.protocol.packets.play.serverbound.ServerboundPlayerLoadedPacket
import io.github.leaflowmc.leaflow.protocol.packets.play.serverbound.ServerboundSetMovementFlagsPacket
import io.github.leaflowmc.leaflow.protocol.packets.play.serverbound.ServerboundSetPositionAndRotationPacket
import io.github.leaflowmc.leaflow.protocol.packets.play.serverbound.ServerboundSetPositionPacket
import io.github.leaflowmc.leaflow.protocol.packets.play.serverbound.ServerboundSetRotationPacket

interface ServerPlayPacketListener : ServerCommonPacketListener, ServerPingPacketListener {
    fun confirmTeleportation(packet: ServerboundConfirmTeleportationPacket)
    fun setPositionAndRotation(packet: ServerboundSetPositionAndRotationPacket)
    fun acknowledgeConfiguration(packet: ServerboundAcknowledgeConfigurationPacket)
    fun clientTickEnd(packet: ServerboundClientTickEndPacket)
    fun setPosition(packer: ServerboundSetPositionPacket)
    fun setRotation(packet: ServerboundSetRotationPacket)
    fun setMovementFlags(packet: ServerboundSetMovementFlagsPacket)
    fun moveVehicle(packet: ServerboundMoveVehiclePacket)
    fun paddleBoat(packet: ServerboundPaddleBoatPacket)
    fun playerCommand(packet: ServerboundPlayerCommandPacket)
    fun playerInput(packet: ServerboundPlayerInputPacket)
    fun playerLoaded(packet: ServerboundPlayerLoadedPacket)
}