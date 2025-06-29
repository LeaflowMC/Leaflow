package io.github.leaflowmc.leaflow.server.packets

import io.github.leaflowmc.leaflow.protocol.ProtocolStage
import io.github.leaflowmc.leaflow.protocol.packets.configuration.ClientboundFinishConfigurationPacket
import io.github.leaflowmc.leaflow.protocol.packets.configuration.ServerboundAcknowledgeFinishConfigurationPacket
import io.github.leaflowmc.leaflow.protocol.packets.configuration.ServerboundClientInfoPacket
import io.github.leaflowmc.leaflow.protocol.packets.configuration.ServerboundKnownPacksPacket
import io.github.leaflowmc.leaflow.server.packets.api.LeaflowServerConfigurationPacketListener
import io.github.leaflowmc.leaflow.server.player.PlayerConnection
import kotlinx.coroutines.ExperimentalCoroutinesApi

@OptIn(ExperimentalCoroutinesApi::class)
class ServerConfigurationPacketListenerImpl(
    override val playerConnection: PlayerConnection
) : ServerCommonPacketListenerImpl(), LeaflowServerConfigurationPacketListener {
    init {
        playerConnection.sendPacket(
            ClientboundFinishConfigurationPacket()
        )
        playerConnection.setOutboundProtocol(ProtocolStage.PLAY)
    }

    override fun clientInfo(packet: ServerboundClientInfoPacket) {
    }

    override fun knownPacks(packet: ServerboundKnownPacksPacket) {
    }

    override fun acknowledgeFinishConfiguration(packet: ServerboundAcknowledgeFinishConfigurationPacket) {
        println("switching to play")
        playerConnection.setInboundProtocol(ProtocolStage.PLAY)
    }
}
