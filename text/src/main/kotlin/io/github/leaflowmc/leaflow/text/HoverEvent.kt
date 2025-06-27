package io.github.leaflowmc.leaflow.text

import io.github.leaflowmc.leaflow.text.component.TextComponent
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonClassDiscriminator

@OptIn(ExperimentalSerializationApi::class)
@Serializable
@JsonClassDiscriminator("action")
sealed interface HoverEvent {
    @SerialName("show_text")
    data class ShowText(val value: TextComponent) : HoverEvent

    // TODO: show item
    // TODO: show entity
}