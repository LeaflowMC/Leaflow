package io.github.leaflowmc.leaflow.protocol.packets.configuration

import io.github.leaflowmc.leaflow.protocol.listener.client.ClientConfigurationPacketListener
import io.github.leaflowmc.leaflow.protocol.packets.ClientPacket
import io.github.leaflowmc.leaflow.protocol.packets.type.ClientConfigurationPackets
import io.github.leaflowmc.leaflow.protocol.packets.type.PacketType
import kotlinx.serialization.Serializable

@Serializable
class ClientboundFinishConfigurationPacket :
    ClientPacket<ClientConfigurationPacketListener, ClientboundFinishConfigurationPacket> {

    override val type: PacketType<ClientboundFinishConfigurationPacket>
        get() = ClientConfigurationPackets.FINISH_CONFIGURATION

    override fun handle(listener: ClientConfigurationPacketListener) {
        listener.finishConfiguration(this)
    }

    override val terminal
        get() = true
}
