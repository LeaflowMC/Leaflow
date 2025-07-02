package io.github.leaflowmc.leaflow.common.types

import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import org.jetbrains.annotations.Range

@Serializable(RGBSerializer::class)
interface RGB {
    val red: @Range(from = 0x00, to = 0xff) Int
    val green: @Range(from = 0x00, to = 0xff) Int
    val blue: @Range(from = 0x00, to = 0xff) Int

    companion object {
        val hexFormat = HexFormat {
            number {
                removeLeadingZeros = true
            }
        }

        fun of(red: Int, green: Int, blue: Int): RGB {
            return RGBImpl(red, green, blue)
        }

        fun of(color: Int): RGB {
            return of(
                color ushr 8 and 0xff,
                color ushr 4 and 0xff,
                color and 0xff
            )
        }
    }
}

fun RGB.toInt(): Int {
    return (red and 0xff shl 8) or (green and 0xff shl 4) or (blue and 0xff)
}
fun RGB.toHexString(): String {
    return toInt().toHexString(RGB.hexFormat)
}

operator fun RGB.component1() = red
operator fun RGB.component2() = green
operator fun RGB.component3() = blue

fun RGB.copy(red: Int? = null, green: Int? = null, blue: Int? = null): RGB {
    return RGB.of(
        red ?: this.red,
        green ?: this.green,
        blue ?: this.blue
    )
}

@Suppress("SERIALIZER_TYPE_INCOMPATIBLE")
@Serializable(RGBSerializer::class)
class RGBImpl(
    override val red: Int,
    override val green: Int,
    override val blue: Int
) : RGB {
    override fun equals(other: Any?): Boolean {
        if (other !is RGB) return false

        return red == other.red &&
                green == other.green &&
                blue == other.blue
    }

    override fun hashCode(): Int {
        var result = red
        result = 31 * result + green
        result = 31 * result + blue
        return result
    }
}

object RGBSerializer : KSerializer<RGB> {
    override val descriptor: SerialDescriptor = PrimitiveSerialDescriptor("RGB", PrimitiveKind.INT)

    override fun serialize(
        encoder: Encoder,
        value: RGB
    ) {
        encoder.encodeInt(value.toInt())
    }

    override fun deserialize(decoder: Decoder): RGB {
        return RGB.of(decoder.decodeInt())
    }

}
