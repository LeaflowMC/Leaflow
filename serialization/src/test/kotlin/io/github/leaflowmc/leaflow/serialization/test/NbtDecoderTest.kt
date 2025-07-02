package io.github.leaflowmc.leaflow.serialization.test

import io.github.leaflowmc.leaflow.serialization.nbt.decodeFromNbt
import io.github.leaflowmc.leaflow.serialization.nbt.encodeToNbt
import net.kyori.adventure.nbt.CompoundBinaryTag
import net.kyori.adventure.nbt.IntArrayBinaryTag
import net.kyori.adventure.nbt.IntBinaryTag
import net.kyori.adventure.nbt.ListBinaryTag
import kotlin.test.Test
import kotlin.test.assertContentEquals
import kotlin.test.assertEquals

class NbtDecoderTest {
    @Test
    fun testCompound() {
        val input = CompoundBinaryTag.builder()
            .putInt("color", 0xff0000)
            .put("passengers",
                ListBinaryTag.builder()
                    .add(CompoundBinaryTag.builder()
                        .putString("name", "p1k0chu")
                        .putInt("age", 69)
                        .build())
                    .add(CompoundBinaryTag.builder()
                        .putString("name", "p2k0chu")
                        .putInt("age", 70)
                        .build())
                    .build())
            .build()

        val output = Car(
            0xff0000,
            listOf(
                Person("p1k0chu", 69),
                Person("p2k0chu", 70)
            )
        )


        assertEquals(output, decodeFromNbt(input))
    }

    @Test
    fun testPrimitive() {
        val input = IntBinaryTag.intBinaryTag(69)
        val output = 69

        assertEquals(output, decodeFromNbt(input))
    }

    @Test
    fun testCollection() {
        val input = ListBinaryTag.builder()
            .add((1..69).map { IntBinaryTag.intBinaryTag(it) })
            .build()

        val output = (1..69).toList()

        assertContentEquals(output, decodeFromNbt<List<Int>>(input))
    }

    @Test
    fun testArray() {
        val input = IntArrayBinaryTag.intArrayBinaryTag(*(1..69).toList().toIntArray())
        val output = (1..69).toList().toIntArray()

        assertContentEquals(output, decodeFromNbt(input))
    }

    @Test
    fun testPolymorphic() {
        val output: Glasses = VisionGlasses(40, 50)

        val input = CompoundBinaryTag.builder()
            .putString("glasses_for", "vision")
            .putInt("vision_left", 40)
            .putInt("vision_right", 50)
            .build()

        assertEquals(output, decodeFromNbt(input))
    }

    @Test
    fun testEnums() {
        val output = ClassWithEnum(
            69,
            TestEnum.ZERO,
            TestEnum.ONE,
            TestEnum.TWO
        )

        val input = CompoundBinaryTag.builder()
            .putInt("number", 69)
            .putString("byteEnum", "zero")
            .putString("varIntEnum", "one")
            .putString("stringEnum", "two")
            .build()

        assertEquals(
            output,
            decodeFromNbt(input)
        )
    }
}