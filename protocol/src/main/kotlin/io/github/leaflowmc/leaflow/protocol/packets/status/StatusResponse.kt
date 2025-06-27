package io.github.leaflowmc.leaflow.protocol.packets.status

import io.github.leaflowmc.leaflow.common.serializer.AnyToStringJsonSerializer
import io.github.leaflowmc.leaflow.text.component.TextComponent
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * @property favicon optional PNG image, without new lines, prepended with
 * `data:image/png;base64,`
 */
@Serializable
class StatusResponse(
    val version: Version,
    val ensuresSecureChat: Boolean,
    val description: TextComponent? = null,
    val favicon: String? = null,
    val players: Players? = null,
) {
    @Serializable
    data class Version(val name: String, val protocol: Int)

    @Serializable
    data class Players(
        val max: Int,
        val online: Int,
        val sample: List<Player>
    ) {
        @Serializable
        data class Player(
            val name: String,
            @SerialName("id")
            val uuid: String
        )
    }
}

object StatusResponseAsStringSerializer : AnyToStringJsonSerializer<StatusResponse>(
    StatusResponse.serializer()
)

typealias StatusResponseAsString = @Serializable(StatusResponseAsStringSerializer::class) StatusResponse

