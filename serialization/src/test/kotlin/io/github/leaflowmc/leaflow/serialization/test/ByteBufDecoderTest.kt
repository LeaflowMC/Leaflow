package io.github.leaflowmc.leaflow.serialization.test

import io.github.leaflowmc.leaflow.common.utils.VarInt
import io.github.leaflowmc.leaflow.common.utils.writePrefixedString
import io.github.leaflowmc.leaflow.common.utils.writeString
import io.github.leaflowmc.leaflow.common.utils.writeVarInt
import io.github.leaflowmc.leaflow.common.utils.byteBufBytes
import io.github.leaflowmc.leaflow.serialization.minecraft_format.decode
import io.github.leaflowmc.leaflow.serialization.minecraft_format.encode
import io.netty.buffer.ByteBufOutputStream
import io.netty.buffer.Unpooled
import net.kyori.adventure.nbt.BinaryTagIO
import net.kyori.adventure.nbt.CompoundBinaryTag
import net.kyori.adventure.nbt.ListBinaryTag
import kotlin.test.Test
import kotlin.test.assertContentEquals
import kotlin.test.assertEquals

class ByteBufDecoderTest {
    @Test
    fun testClass() {
        val output = Car(
            0xff0000,
            listOf(
                Person("p1k0chu", 69),
                Person("p2k0chu", 70)
            )
        )

        val input = Unpooled.buffer().apply {
            writeInt(0xff0000)
            writeVarInt(2)
            writePrefixedString("p1k0chu")
            writeInt(69)
            writePrefixedString("p2k0chu")
            writeInt(70)
        }

        assertEquals(
            output,
            input.decode<Car>()
        )
    }

    @Test
    fun testPrimitive() {
        val output = "69"

        val input = Unpooled.buffer().apply {
            writePrefixedString("69")
        }

        assertEquals(
            output,
            input.decode<String>()
        )
    }

    @Test
    fun testVarIntPrimitive() {
        val output = VarInt(5)

        val input = Unpooled.buffer().apply {
            writeVarInt(5)
        }

        assertEquals(
            output,
            input.decode<VarInt>()
        )
    }

    @Test
    fun testCollection() {
        val output = (1..69).toList()

        val input = Unpooled.buffer().apply {
            writeVarInt(69)

            (1..69).forEach(::writeInt)
        }

        assertContentEquals(
            output,
            input.decode<List<Int>>()
        )
    }

    @Test
    fun `test list of VarInt`() {
        val output: List<VarInt> = (1..69).map(::VarInt)

        val input = Unpooled.buffer().apply {
            writeVarInt(69)

            (1..69).forEach(::writeVarInt)
        }

        assertContentEquals(
            output,
            input.decode<List<VarInt>>()
        )
    }

    @Test
    fun testWithNbt() {
        val output = Something(
            CarAsNbt(
                0x00ff00,
                listOf(
                    Person("p1k0chu", 69),
                    Person("p2k0chu", 70)
                )
            ),
            Person("p3k0chu", 18)
        )

        val input = Unpooled.buffer().apply {
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

        assertEquals(output, input.decode<Something>())
    }

    @Test
    fun testEnum() {
        val output = ClassWithEnum(
            69,
            TestEnum.ONE,
            TestEnum.TWO
        )

        val input = Unpooled.buffer().apply {
            writeInt(69)
            writeByte(1)
            writeVarInt(2)
        }

        assertEquals(
            output,
            input.decode<ClassWithEnum>()
        )
    }

    @Test
    fun testNonLengthPrefixedCollection() {
        val output = ClassWithFixedLength(
            (1..69).toList().toIntArray(),
            "hello",
            (1..169).map(Int::toByte).toByteArray()
        )

        val input = Unpooled.buffer().apply {
            (1..69).forEach(::writeInt)
            writeString("hello")
            (1..169).forEach(::writeByte)
        }

        assertEquals(
            output,
            input.decode<ClassWithFixedLength>()
        )
    }
}