package io.github.leaflowmc.leaflow.server.packets

import io.github.leaflowmc.leaflow.protocol.packets.ping.ClientboundPongPacket
import io.github.leaflowmc.leaflow.protocol.packets.ping.ServerboundPingPacket
import io.github.leaflowmc.leaflow.protocol.packets.play.serverbound.*
import io.github.leaflowmc.leaflow.server.packets.api.LeaflowServerPlayPacketListener
import io.github.leaflowmc.leaflow.server.player.PlayerConnection

class ServerPlayPacketListenerImpl(
    override val playerConnection: PlayerConnection
) : ServerCommonPacketListenerImpl(), LeaflowServerPlayPacketListener {
    override fun pingRequest(packet: ServerboundPingPacket) {
        playerConnection.sendPacket(ClientboundPongPacket(packet.timestamp))
    }

    override fun confirmTeleportation(packet: ServerboundConfirmTeleportationPacket) {
    }

    override fun setPositionAndRotation(packet: ServerboundSetPositionAndRotationPacket) {
    }

    override fun acknowledgeConfiguration(packet: ServerboundAcknowledgeConfigurationPacket) {
    }

    override fun clientTickEnd(packet: ServerboundClientTickEndPacket) {
    }

    override fun setPosition(packer: ServerboundSetPositionPacket) {
    }

    override fun setRotation(packet: ServerboundSetRotationPacket) {
    }

    override fun setMovementFlags(packet: ServerboundSetMovementFlagsPacket) {
    }

    override fun moveVehicle(packet: ServerboundMoveVehiclePacket) {
    }

    override fun paddleBoat(packet: ServerboundPaddleBoatPacket) {
    }

    override fun playerCommand(packet: ServerboundPlayerCommandPacket) {
    }

    override fun playerInput(packet: ServerboundPlayerInputPacket) {
    }

    override fun playerLoaded(packet: ServerboundPlayerLoadedPacket) {
    }
}