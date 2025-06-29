package io.github.leaflowmc.leaflow.protocol.packets.common

import io.github.leaflowmc.leaflow.protocol.listener.client.ClientCommonPacketListener
import io.github.leaflowmc.leaflow.protocol.packets.ClientPacket
import kotlinx.serialization.Serializable

/**
 * Custom report details map.
 *
 * Key is Title, value is Description
 */
typealias ReportDetails = Map<String, String>

@Serializable
class ClientboundCustomReportDetailsPacket(val details: ReportDetails) : ClientPacket<ClientCommonPacketListener> {
    override fun handle(listener: ClientCommonPacketListener) {
        listener.customReportDetails(this)
    }
}