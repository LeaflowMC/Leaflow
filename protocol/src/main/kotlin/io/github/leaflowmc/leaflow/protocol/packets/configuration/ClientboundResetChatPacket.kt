package io.github.leaflowmc.leaflow.protocol.packets.configuration

import io.github.leaflowmc.leaflow.protocol.listener.client.ClientConfigurationPacketListener
import io.github.leaflowmc.leaflow.protocol.packets.ClientPacket
import io.github.leaflowmc.leaflow.protocol.packets.type.ClientConfigurationPackets
import io.github.leaflowmc.leaflow.protocol.packets.type.PacketType
import kotlinx.serialization.Serializable

@Serializable
class ClientboundResetChatPacket : ClientPacket<ClientConfigurationPacketListener, ClientboundResetChatPacket> {
    override val type: PacketType<ClientboundResetChatPacket>
        get() = ClientConfigurationPackets.RESET_CHAT

    override fun handle(listener: ClientConfigurationPacketListener) {
        listener.resetChat(this)
    }
}