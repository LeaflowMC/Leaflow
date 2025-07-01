package io.github.leaflowmc.leaflow.common.test

import io.github.leaflowmc.leaflow.common.Identifier
import io.github.leaflowmc.leaflow.common.IdentifierImpl
import io.github.leaflowmc.leaflow.common.asString
import kotlinx.serialization.json.Json
import kotlin.test.Test
import kotlin.test.assertEquals

class IdentifierSerializerTest {
    @Test
    fun testSerialize() {
        val id = Identifier.ofVanilla("ice_cream")

        val output = Json.encodeToString(id)

        assertEquals("\"minecraft:ice_cream\"", output)
    }

    @Test
    fun testSerializeImpl() {
        val id = IdentifierImpl("minecraft", "ice_cream")

        val output = Json.encodeToString(id)

        assertEquals("\"minecraft:ice_cream\"", output)
    }

    @Test
    fun testDeserialize() {
        val input = "\"minecraft:ice_cream\""
        val output = Identifier.ofVanilla("ice_cream")

        assertEquals(output, Json.decodeFromString<Identifier>(input))
    }
}