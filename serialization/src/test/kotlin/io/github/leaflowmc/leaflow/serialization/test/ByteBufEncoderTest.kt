package io.github.leaflowmc.leaflow.serialization.test

import io.github.leaflowmc.leaflow.common.utils.VarInt
import io.github.leaflowmc.leaflow.common.utils.writePrefixedString
import io.github.leaflowmc.leaflow.common.utils.writeString
import io.github.leaflowmc.leaflow.common.utils.writeVarInt
import io.github.leaflowmc.leaflow.common.utils.byteBufBytes
import io.github.leaflowmc.leaflow.serialization.annotations.NotLengthPrefixed
import io.github.leaflowmc.leaflow.serialization.minecraft_format.encode
import io.netty.buffer.ByteBufOutputStream
import kotlinx.serialization.Serializable
import kotlinx.serialization.builtins.serializer
import net.kyori.adventure.nbt.BinaryTagIO
import net.kyori.adventure.nbt.CompoundBinaryTag
import net.kyori.adventure.nbt.ListBinaryTag
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

    @Test
    fun testWithNbt() {
        val input = Something(
            Car(
                0x00ff00,
                listOf(
                    Person("p1k0chu", 69),
                    Person("p2k0chu", 70)
                )
            ),
            Person("p3k0chu", 18)
        )

        val output = byteBufBytes {
            BinaryTagIO.writer()
                .writeNameless(
                    CompoundBinaryTag.builder()
                        .putInt("color", 0x00ff00)
                        .put("passengers", ListBinaryTag.builder()
                            .add(CompoundBinaryTag.builder()
                                .putString("name", "p1k0chu")
                                .putInt("age", 69)
                                .build())
                            .add(CompoundBinaryTag.builder()
                                .putString("name", "p2k0chu")
                                .putInt("age", 70)
                                .build())
                            .build())
                        .build(),
                    ByteBufOutputStream(this),
                    BinaryTagIO.Compression.NONE
                )

            writePrefixedString("p3k0chu")
            writeInt(18)
        }

        assertContentEquals(output, byteBufBytes { encode(input) })
    }

    @Test
    fun testEnum() {
        val input = ClassWithEnum(
            69,
            TestEnum.ONE,
            TestEnum.TWO
        )

        val output = byteBufBytes {
            writeInt(69)
            writeByte(1)
            writeVarInt(2)
        }

        assertContentEquals(
            output,
            byteBufBytes { encode(input) }
        )
    }

    @Test
    fun testNonLengthPrefixedCollection() {
        val input = ClassWithFixedLength(
            (1..69).toList().toIntArray(),
            "hello",
            (1..169).map(Int::toByte).toByteArray()
        )

        val output = byteBufBytes {
            (1..69).forEach(::writeInt)
            writeString("hello")
            (1..169).forEach(::writeByte)
        }

        assertContentEquals(
            output,
            byteBufBytes { encode(input) }
        )
    }
}