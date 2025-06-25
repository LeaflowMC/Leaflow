package io.github.leaflowmc.leaflow.serialization.test

import io.github.leaflowmc.leaflow.common.utils.VarInt
import io.github.leaflowmc.leaflow.commonTest.utils.byteBufBytes
import io.github.leaflowmc.leaflow.common.utils.writePrefixedString
import io.github.leaflowmc.leaflow.common.utils.writeVarInt
import io.github.leaflowmc.leaflow.serialization.minecraft_format.encode
import kotlin.test.Test
import kotlin.test.assertContentEquals

class ByteBufEncoderTest {
    @Test
    fun testClass() {
        val input = Car(
            0xff0000,
            listOf(
                Person("p1k0chu", 69),
                Person("p2k0chu", 70)
            )
        )

        val output = byteBufBytes {
            writeInt(0xff0000)
            writeVarInt(2)
            writePrefixedString("p1k0chu")
            writeInt(69)
            writePrefixedString("p2k0chu")
            writeInt(70)
        }

        assertContentEquals(
            output,
            byteBufBytes { encode(input) }
        )
    }

    @Test
    fun testPrimitive() {
        val input = "69"

        val output = byteBufBytes {
            writePrefixedString(input)
        }

        assertContentEquals(
            output,
            byteBufBytes { encode(input) }
        )
    }

    @Test
    fun testCollection() {
        val input = (1..69).toList()

        val output = byteBufBytes {
            writeVarInt(69)

            (1..69).forEach(::writeInt)
        }

        assertContentEquals(
            output,
            byteBufBytes { encode(input) }
        )
    }

    @Test
    fun `test list of VarInt`() {
        val input: List<VarInt> = (1..69).map(::VarInt)

        val output = byteBufBytes {
            writeVarInt(69)

            (1..69).forEach(::writeVarInt)
        }

        assertContentEquals(
            output,
            byteBufBytes { encode(input) }
        )
    }
}