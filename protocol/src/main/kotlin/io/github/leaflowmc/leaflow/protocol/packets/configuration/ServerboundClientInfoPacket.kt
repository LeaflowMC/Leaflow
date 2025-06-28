package io.github.leaflowmc.leaflow.protocol.packets.configuration

import io.github.leaflowmc.leaflow.protocol.listener.server.ServerConfigurationPacketListener
import io.github.leaflowmc.leaflow.protocol.packets.ServerPacket
import io.github.leaflowmc.leaflow.protocol.packets.type.PacketType
import io.github.leaflowmc.leaflow.protocol.packets.type.ServerConfigurationPackets
import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import java.util.*

@Serializable
class ServerboundClientInfoPacket(
    val locale: String,
    val viewDistance: Byte,
    val chatMode: ChatMode,
    val chatColorsEnabled: Boolean,
    val displayedSkinParts: DisplayedSkinParts,
    val mainHand: MainHand,
    val enableTextFiltering: Boolean,
    val allowListing: Boolean,
    val particleStatus: ParticleStatus
) : ServerPacket<ServerConfigurationPacketListener, ServerboundClientInfoPacket> {
    override val type: PacketType<ServerboundClientInfoPacket>
        get() = ServerConfigurationPackets.CLIENT_INFORMATION

    override fun handle(listener: ServerConfigurationPacketListener) {
        listener.clientInfo(this)
    }

    enum class ChatMode {
        ENABLED,
        COMMANDS_ONLY,
        HIDDEN;
    }

    @Serializable(DisplayedSkinPartsSerializer::class)
    class DisplayedSkinParts(bitmask: Byte) {
        private val bitSet = BitSet.valueOf(byteArrayOf(bitmask))

        var cape: Boolean
            get() = bitSet.get(0)
            set(value) = bitSet.set(0, value)
        var jacket: Boolean
            get() = bitSet.get(1)
            set(value) = bitSet.set(1, value)
        var leftSleeve: Boolean
            get() = bitSet.get(2)
            set(value) = bitSet.set(2, value)
        var rightSleeve: Boolean
            get() = bitSet.get(3)
            set(value) = bitSet.set(3, value)
        var leftLeg: Boolean
            get() = bitSet.get(4)
            set(value) = bitSet.set(4, value)
        var rightLeg: Boolean
            get() = bitSet.get(5)
            set(value) = bitSet.set(5, value)
        var hat: Boolean
            get() = bitSet.get(6)
            set(value) = bitSet.set(6, value)

        val bitMask: Byte get() = bitSet.toByteArray()[0]
    }

    enum class MainHand {
        LEFT,
        RIGHT;
    }

    enum class ParticleStatus {
        ALL,
        DECREASED,
        MINIMAL;
    }
}

object DisplayedSkinPartsSerializer : KSerializer<ServerboundClientInfoPacket.DisplayedSkinParts> {
    override val descriptor: SerialDescriptor = PrimitiveSerialDescriptor("DisplayedSkinParts", PrimitiveKind.BYTE)

    override fun serialize(
        encoder: Encoder,
        value: ServerboundClientInfoPacket.DisplayedSkinParts
    ) {
        encoder.encodeByte(value.bitMask)
    }

    override fun deserialize(decoder: Decoder): ServerboundClientInfoPacket.DisplayedSkinParts {
        return ServerboundClientInfoPacket.DisplayedSkinParts(decoder.decodeByte())
    }
}