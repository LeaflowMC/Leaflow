package io.github.leaflowmc.leaflow.registry.types

import io.github.leaflowmc.leaflow.text.component.TextComponent
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class JukeboxSong(
    @SerialName("sound_event")
    val soundEvent: Nothing, // TODO: sound event
    val description: TextComponent,
    @SerialName("length_in_seconds")
    val lengthInSeconds: Float,
    @SerialName("comparator_output")
    val comparatorOutput: Int
) {
}