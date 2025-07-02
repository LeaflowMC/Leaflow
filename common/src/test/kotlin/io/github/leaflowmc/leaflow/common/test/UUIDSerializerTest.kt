package io.github.leaflowmc.leaflow.common.test

import io.github.leaflowmc.leaflow.common.types.GameProfile
import io.github.leaflowmc.leaflow.common.serializer.UUIDAsStringSerializer
import io.github.leaflowmc.leaflow.common.utils.writePrefixedString
import io.github.leaflowmc.leaflow.common.utils.writeVarInt
import io.github.leaflowmc.leaflow.common.utils.byteBufBytes
import io.github.leaflowmc.leaflow.serialization.minecraft_format.encode
import kotlinx.serialization.json.Json
import kotlinx.serialization.modules.serializersModuleOf
import java.util.*
import kotlin.test.Test
import kotlin.test.assertContentEquals
import kotlin.test.assertEquals

class UUIDSerializerTest {
    private val uuid = UUID.randomUUID()
    private val module = serializersModuleOf(UUIDAsStringSerializer)
    private val json = Json { serializersModule = module }

    @Test
    fun testContextual() {
        val input = GameProfile(
            uuid,
            "username",
            listOf()
        )

        val output = "{\"id\":\"$uuid\",\"name\":\"username\",\"properties\":[]}"

        assertEquals(output, json.encodeToString(input))
    }

    @Test
    fun testDefault() {
        val input = GameProfile(
            uuid,
            "username",
            listOf()
        )

        val output = byteBufBytes {
            writeLong(uuid.mostSignificantBits)
            writeLong(uuid.leastSignificantBits)
            writePrefixedString("username")
            writeVarInt(0)
        }

        assertContentEquals(
            output,
            byteBufBytes { encode(input) }
        )
    }
}
