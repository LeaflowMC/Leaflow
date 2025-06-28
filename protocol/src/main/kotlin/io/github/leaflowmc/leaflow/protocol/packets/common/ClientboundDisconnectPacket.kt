package io.github.leaflowmc.leaflow.protocol.packets.common

import io.github.leaflowmc.leaflow.protocol.listener.client.ClientCommonPacketListener
import io.github.leaflowmc.leaflow.protocol.listener.client.ClientConfigurationPacketListener
import io.github.leaflowmc.leaflow.protocol.packets.ClientPacket
import io.github.leaflowmc.leaflow.protocol.packets.type.ClientConfigurationPackets
import io.github.leaflowmc.leaflow.protocol.packets.type.PacketType
import io.github.leaflowmc.leaflow.text.component.TextComponentAsNbt
import kotlinx.serialization.Serializable

interface ClientboundDisconnectPacket<L : ClientCommonPacketListener, T : ClientboundDisconnectPacket<L, T>> :
    ClientPacket<ClientCommonPacketListener, T> {
    val reason: TextComponentAsNbt

    override fun handle(listener: ClientCommonPacketListener) {
        listener.disconnect(this)
    }
}

@Serializable
class ClientboundConfigurationDisconnectPacket(override val reason: TextComponentAsNbt) :
    ClientboundDisconnectPacket<ClientConfigurationPacketListener, ClientboundConfigurationDisconnectPacket> {

    override val type: PacketType<ClientboundConfigurationDisconnectPacket>
        get() = ClientConfigurationPackets.DISCONNECT
}
