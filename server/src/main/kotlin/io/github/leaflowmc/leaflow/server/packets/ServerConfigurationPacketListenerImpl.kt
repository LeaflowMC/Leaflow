package io.github.leaflowmc.leaflow.server.packets

import io.github.leaflowmc.leaflow.protocol.ProtocolStage
import io.github.leaflowmc.leaflow.protocol.listener.server.ServerConfigurationPacketListener
import io.github.leaflowmc.leaflow.protocol.packets.common.ClientboundConfigurationKeepAlivePacket
import io.github.leaflowmc.leaflow.protocol.packets.common.ClientboundKeepAlivePacket
import io.github.leaflowmc.leaflow.protocol.packets.configuration.ClientboundFinishConfigurationPacket
import io.github.leaflowmc.leaflow.protocol.packets.configuration.ServerboundAcknowledgeFinishConfigurationPacket
import io.github.leaflowmc.leaflow.protocol.packets.configuration.ServerboundClientInfoPacket
import io.github.leaflowmc.leaflow.protocol.packets.configuration.ServerboundKnownPacksPacket
import io.github.leaflowmc.leaflow.protocol.packets.ping.ClientboundConfigurationPingPacket
import io.github.leaflowmc.leaflow.protocol.packets.ping.ClientboundPingPacket
import io.github.leaflowmc.leaflow.server.packets.api.LeaflowServerConfigurationPacketListener
import io.github.leaflowmc.leaflow.server.player.PlayerConnection
import kotlinx.coroutines.*
import org.apache.logging.log4j.LogManager
import kotlin.time.Duration.Companion.seconds
import kotlin.time.DurationUnit

@OptIn(ExperimentalCoroutinesApi::class)
class ServerConfigurationPacketListenerImpl(
    override val playerConnection: PlayerConnection
) : ServerCommonPacketListenerImpl(), LeaflowServerConfigurationPacketListener {
    init {
        playerConnection.sendPacket(
            ClientboundFinishConfigurationPacket()
        )
    }

    override fun clientInfo(packet: ServerboundClientInfoPacket) {
    }

    override fun knownPacks(packet: ServerboundKnownPacksPacket) {
    }

    override fun acknowledgeFinishConfiguration(packet: ServerboundAcknowledgeFinishConfigurationPacket) {
        println("switching to play")
        playerConnection.setProtocol(ProtocolStage.PLAY)
    }

    override fun getPingPacket(timestamp: Int): ClientboundPingPacket<*, *> {
        return ClientboundConfigurationPingPacket(timestamp)
    }

    override fun getKeepAlivePacket(id: Long): ClientboundKeepAlivePacket<*, *> {
        return ClientboundConfigurationKeepAlivePacket(id)
    }
}
