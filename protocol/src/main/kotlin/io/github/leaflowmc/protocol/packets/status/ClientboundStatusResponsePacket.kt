package io.github.leaflowmc.protocol.packets.status

import io.github.leaflowmc.protocol.listener.client.ClientStatusPacketListener
import io.github.leaflowmc.protocol.packets.ClientPacket
import io.github.leaflowmc.protocol.packets.type.ClientStatusPackets
import io.github.leaflowmc.protocol.packets.type.PacketType
import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.descriptors.buildClassSerialDescriptor
import kotlinx.serialization.descriptors.element
import kotlinx.serialization.encoding.CompositeDecoder
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import kotlinx.serialization.encoding.decodeStructure
import kotlinx.serialization.encoding.encodeStructure
import kotlinx.serialization.json.Json

@Serializable(ClientboundStatusResponsePacketSerializer::class)
data class ClientboundStatusResponsePacket(
    val jsonResponse: StatusResponse
) : ClientPacket<ClientStatusPacketListener, ClientboundStatusResponsePacket> {
    override fun getType(): PacketType<ClientboundStatusResponsePacket> {
        return ClientStatusPackets.STATUS_RESPONSE
    }

    override fun handle(listener: ClientStatusPacketListener) {
        listener.statusResponse(this)
    }
}

object ClientboundStatusResponsePacketSerializer : KSerializer<ClientboundStatusResponsePacket> {
    override val descriptor: SerialDescriptor =
        buildClassSerialDescriptor(ClientboundStatusResponsePacket::class.qualifiedName!!) {
            element<String>("jsonResponse")
        }

    override fun serialize(
        encoder: Encoder,
        value: ClientboundStatusResponsePacket
    ) {
        encoder.encodeStructure(descriptor) {
            encodeStringElement(descriptor, 0, Json.encodeToString(value.jsonResponse))
        }
    }

    override fun deserialize(decoder: Decoder): ClientboundStatusResponsePacket {
        return decoder.decodeStructure(descriptor) {
            var json: String? = null

            while (true) {
                when (val index = decodeElementIndex(descriptor)) {
                    0 -> json = decodeStringElement(descriptor, 0)

                    CompositeDecoder.DECODE_DONE -> break
                    else -> error("Unexpected index: $index")
                }
            }
            require(json != null) { "jsonResponse wasn't decoded" }
            ClientboundStatusResponsePacket(Json.decodeFromString(json))
        }
    }
}