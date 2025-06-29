package io.github.leaflowmc.leaflow.protocol.listener.client

import io.github.leaflowmc.leaflow.protocol.packets.common.ClientboundCustomReportDetailsPacket
import io.github.leaflowmc.leaflow.protocol.packets.common.ClientboundDisconnectPacket
import io.github.leaflowmc.leaflow.protocol.packets.common.ClientboundKeepAlivePacket
import io.github.leaflowmc.leaflow.protocol.packets.ping.ClientboundPingPacket

interface ClientCommonPacketListener : ClientPacketListener {
    fun keepAlive(packet: ClientboundKeepAlivePacket)
    fun ping(packet: ClientboundPingPacket)
    fun disconnect(packet: ClientboundDisconnectPacket)
    fun customReportDetails(packet: ClientboundCustomReportDetailsPacket)
}