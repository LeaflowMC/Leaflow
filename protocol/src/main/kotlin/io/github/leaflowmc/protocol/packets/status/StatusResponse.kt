package io.github.leaflowmc.protocol.packets.status

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * @property favicon optional PNG image, without new lines, prepended with
 * `data:image/png;base64,`
 */
@Serializable
class StatusResponse(
    val version: Version,
    val players: Players?,
    // TODO: description: TextComponent
    val favicon: String?,
    val ensuresSecureChat: Boolean
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