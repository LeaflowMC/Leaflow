package io.github.leaflowmc.leaflow.protocol.packets.type

import io.github.leaflowmc.leaflow.protocol.listener.client.ClientStatusPacketListener
import io.github.leaflowmc.leaflow.protocol.packets.ClientPacket
import io.github.leaflowmc.leaflow.protocol.packets.ping.ClientboundStatusPongResponsePacket
import io.github.leaflowmc.leaflow.protocol.packets.status.ClientboundStatusResponsePacket

object ClientStatusPackets : ProtocolInfo<ClientStatusPacketListener, ClientPacket<ClientStatusPacketListener, *>>() {
    val STATUS_RESPONSE = addPacket(ClientboundStatusResponsePacket.serializer())
    val PONG_RESPONSE = addPacket(ClientboundStatusPongResponsePacket.serializer())
}