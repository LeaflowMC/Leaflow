package io.github.leaflowmc.leaflow.protocol.packets.common

import io.github.leaflowmc.leaflow.protocol.listener.client.ClientCommonPacketListener
import io.github.leaflowmc.leaflow.protocol.listener.client.ClientConfigurationPacketListener
import io.github.leaflowmc.leaflow.protocol.packets.ClientPacket
import io.github.leaflowmc.leaflow.protocol.packets.type.ClientConfigurationPackets
import io.github.leaflowmc.leaflow.protocol.packets.type.PacketType
import kotlinx.serialization.Serializable

/**
 * Custom report details map.
 *
 * Key is Title, value is Description
 */
typealias ReportDetails = Map<String, String>

interface ClientboundCustomReportDetailsPacket<L : ClientCommonPacketListener, T : ClientboundCustomReportDetailsPacket<L, T>> :
    ClientPacket<L, T> {
    val details: ReportDetails

    override fun handle(listener: L) {
        listener.customReportDetails(this)
    }
}

@Serializable
class ClientboundConfigurationCustomReportDetails(override val details: ReportDetails) :
    ClientboundCustomReportDetailsPacket<ClientConfigurationPacketListener, ClientboundConfigurationCustomReportDetails> {

    override val type: PacketType<ClientboundConfigurationCustomReportDetails>
        get() = ClientConfigurationPackets.CUSTOM_REPORT_DETAILS
}
