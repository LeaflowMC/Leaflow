package io.github.leaflowmc.protocol.packets.type

import io.github.leaflowmc.protocol.listener.client.ClientStatusPacketListener
import io.github.leaflowmc.protocol.packets.ClientPacket
import io.github.leaflowmc.protocol.packets.status.ClientboundStatusResponsePacket

object ClientStatusPackets : ProtocolInfo<ClientStatusPacketListener, ClientPacket<ClientStatusPacketListener, *>>() {
    val STATUS_RESPONSE = addPacket(ClientboundStatusResponsePacket.serializer())
}