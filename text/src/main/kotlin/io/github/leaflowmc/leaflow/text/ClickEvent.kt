package io.github.leaflowmc.leaflow.text

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonClassDiscriminator

@OptIn(ExperimentalSerializationApi::class)
@Serializable
@JsonClassDiscriminator("action")
sealed interface ClickEvent {
    @Serializable
    @SerialName("open_url")
    data class OpenUrl(val url: String) : ClickEvent

    @Serializable
    @SerialName("open_file")
    data class OpenFile(val path: String) : ClickEvent

    @Serializable
    @SerialName("run_command")
    data class RunCommand(val command: String) : ClickEvent

    @Serializable
    @SerialName("suggest_command")
    data class SuggestCommand(val command: String) : ClickEvent

    @Serializable
    @SerialName("change_page")
    data class ChangePage(val page: Int) : ClickEvent

    @Serializable
    @SerialName("copy_to_clipboard")
    data class CopyToClipboard(val value: String) : ClickEvent

    // TODO: show_dialog

    @Serializable
    @SerialName("custom")
    data class Custom(val id: String, val payload: String) : ClickEvent
}