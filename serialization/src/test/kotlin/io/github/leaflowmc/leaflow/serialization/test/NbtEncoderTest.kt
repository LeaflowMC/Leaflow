package io.github.leaflowmc.leaflow.serialization.test

import io.github.leaflowmc.leaflow.serialization.nbt.encodeToNbt
import net.kyori.adventure.nbt.CompoundBinaryTag
import net.kyori.adventure.nbt.IntArrayBinaryTag
import net.kyori.adventure.nbt.IntBinaryTag
import net.kyori.adventure.nbt.ListBinaryTag
import kotlin.test.Test
import kotlin.test.assertEquals

class NbtEncoderTest {
    @Test
    fun testCompound() {
        val input = Car(
            0xff0000,
            listOf(
                Person("p1k0chu", 69),
                Person("p2k0chu", 70)
            )
        )

        val output = CompoundBinaryTag.builder()
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

        assertEquals(output, encodeToNbt(input))
    }

    @Test
    fun testPrimitive() {
        val input = 69

        val output = IntBinaryTag.intBinaryTag(69)

        assertEquals(output, encodeToNbt(input))
    }

    @Test
    fun testCollection() {
        val input = (1..69).toList()

        val output = ListBinaryTag.builder()
            .add((1..69).map { IntBinaryTag.intBinaryTag(it) })
            .build()

        assertEquals(output, encodeToNbt(input))
    }

    @Test
    fun testArray() {
        val input = (1..69).toList().toIntArray()
        val output = IntArrayBinaryTag.intArrayBinaryTag(*(1..69).toList().toIntArray())

        assertEquals(output, encodeToNbt(input))
    }
}

