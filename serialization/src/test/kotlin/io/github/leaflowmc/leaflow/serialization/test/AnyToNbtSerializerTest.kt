package io.github.leaflowmc.leaflow.serialization.test

import io.github.leaflowmc.leaflow.common.utils.byteBufBytes
import io.github.leaflowmc.leaflow.serialization.minecraft_format.decode
import io.github.leaflowmc.leaflow.serialization.minecraft_format.encode
import io.github.leaflowmc.leaflow.common.serializer.AnyToNbtSerializer
import io.netty.buffer.Unpooled
import kotlinx.serialization.Serializable
import net.kyori.adventure.nbt.BinaryTagIO
import net.kyori.adventure.nbt.CompoundBinaryTag
import java.io.ByteArrayOutputStream
import kotlin.test.Test
import kotlin.test.assertContentEquals
import kotlin.test.assertEquals

class AnyToNbtSerializerTest {
    @Test
    fun testWriteRead() {
        val nbt = CompoundBinaryTag.builder()
            .putString("name", "p1k0chu")
            .putInt("age", 69)
            .build()

        val person: PersonAsNbt = Person("p1k0chu", 69)

        val buffer = Unpooled.buffer()

        ByteArrayOutputStream()
            .also {
                BinaryTagIO.writer()
                    .writeNameless(nbt, it, BinaryTagIO.Compression.NONE)
            }
            .toByteArray()
            .also { expected ->
                assertContentEquals(
                    expected,
                    byteBufBytes {
                        encode(PersonAsNbtSerializer, person)
                    }
                )
            }
            .also(buffer::writeBytes)

        assertEquals(person, buffer.decode(PersonAsNbtSerializer))
    }
}

object PersonAsNbtSerializer : AnyToNbtSerializer<Person>(Person.serializer())

typealias PersonAsNbt = @Serializable(PersonAsNbtSerializer::class) Person
