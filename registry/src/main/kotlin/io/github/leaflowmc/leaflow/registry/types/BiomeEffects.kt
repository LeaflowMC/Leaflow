package io.github.leaflowmc.leaflow.registry.types

import io.github.leaflowmc.leaflow.common.types.RGB
import io.github.leaflowmc.leaflow.serialization.annotations.ProtocolEnumKind
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class BiomeEffects(
    @SerialName("fog_color")
    val fogColor: RGB,
    @SerialName("water_color")
    val waterColor: RGB,
    @SerialName("water_fog_color")
    val waterFogColor: RGB,
    @SerialName("sky_color")
    val skyColor: RGB,
    @SerialName("foliage_color")
    val foliageColor: RGB?,
    @SerialName("dry_foliage_color")
    val dryFoliageColor: RGB?,
    @SerialName("grass_color")
    val grassColor: RGB?,
    @SerialName("grass_color_modifier")
    val grassColorModifier: GrassColorModifier,
    val particle: Nothing?, // TODO: particle
    @SerialName("ambient_sound")
    val ambientSound: Nothing?, // TODO: sound event
    @SerialName("mood_sound")
    val moodSound: Nothing?, // TODO
    @SerialName("additions_sound")
    val additionsSound: Nothing?, // TODO
    val music: List<Nothing>?, // TODO
    @SerialName("music_volume")
    val musicVolume: Float = 1f
) {
}

enum class GrassColorModifier {
    NONE,
    DARK_FOREST,
    SWAMP;
}
