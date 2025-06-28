package io.github.leaflowmc.leaflow.server.utils

import io.github.leaflowmc.leaflow.common.GameProfile
import io.github.leaflowmc.leaflow.common.serializer.UUIDAsStringSerializer
import kotlinx.serialization.json.Json
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.contextual
import java.math.BigInteger
import java.net.URI
import java.net.URLEncoder
import java.net.http.HttpClient
import java.net.http.HttpRequest
import java.net.http.HttpResponse
import java.nio.charset.StandardCharsets
import java.nio.charset.StandardCharsets.UTF_8
import java.security.Key
import java.security.MessageDigest

object AuthUtils {
    const val AUTH_URL = "https://sessionserver.mojang.com/session/minecraft/hasJoined?username=%s&serverId=%s"
    const val DIGEST_ALGORITHM = "SHA-1"

    private val httpClient = HttpClient.newHttpClient()

    private val json = Json {
        serializersModule = SerializersModule {
            contextual(UUIDAsStringSerializer)
        }
        ignoreUnknownKeys = true
    }

    fun serverId(baseServerId: String, secretKey: Key, publicKey: Key): String {
        val messageDigest = MessageDigest.getInstance(DIGEST_ALGORITHM)

        messageDigest.update(baseServerId.toByteArray(StandardCharsets.ISO_8859_1))
        messageDigest.update(secretKey.encoded)
        messageDigest.update(publicKey.encoded)

        return BigInteger(messageDigest.digest()).toString(16)
    }

    fun playerHasJoined(name: String, serverId: String): GameProfile {
        val uri = String.format(
            AUTH_URL,
            URLEncoder.encode(name, UTF_8),
            serverId
        ).let(::URI)

        val request = HttpRequest.newBuilder()
            .uri(uri)
            .build()

        val response = httpClient.send(request, HttpResponse.BodyHandlers.ofString())

        val body = response.body()
        check(body.isNotEmpty()) { "auth response is empty" }

        return json.decodeFromString<GameProfile>(body)
    }

}
