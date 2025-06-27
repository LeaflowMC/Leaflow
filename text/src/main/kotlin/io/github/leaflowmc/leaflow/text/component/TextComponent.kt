package io.github.leaflowmc.leaflow.text.component

import io.github.leaflowmc.leaflow.common.serializer.AnyToNbtSerializer
import io.github.leaflowmc.leaflow.common.serializer.AnyToStringJsonSerializer
import io.github.leaflowmc.leaflow.text.ClickEvent
import io.github.leaflowmc.leaflow.text.HoverEvent
import kotlinx.serialization.Serializable

@Serializable
sealed class TextComponent {
    abstract val extra: List<TextComponent>?

    abstract val color: String?
    abstract val font: String?
    abstract val bold: Boolean?
    abstract val italic: Boolean?
    abstract val underlined: Boolean?
    abstract val strikethrough: Boolean?
    abstract val obfuscated: Boolean?
    abstract val shadowColor: List<Float>?
    abstract val insertion: String?
    abstract val clickEvent: ClickEvent?
    abstract val hoverEvent: HoverEvent?

    init {
        shadowColor?.let {
            require(it.size == 4) { "shadow color array length MUST be 4" }
            require(it.all { it in 0f..1f }) { "all values in shadow color must be between 0f and 1f" }
        }
    }

    abstract class Builder {
        val extra = mutableListOf<TextComponent>()

        var color: String? = null
        var font: String? = null
        var bold: Boolean? = null
        var italic: Boolean? = null
        var underlined: Boolean? = null
        var strikethrough: Boolean? = null
        var obfuscated: Boolean? = null
        var shadowColor: List<Float>? = null
        var insertion: String? = null
        var clickEvent: ClickEvent? = null
        var hoverEvent: HoverEvent? = null

        fun setShadowColor(red: Int, green: Int, blue: Int, alpha: Int) {
            require(red in 0..255) { "red must be between 0 and 255" }
            require(green in 0..255) { "green must be between 0 and 255" }
            require(blue in 0..255) { "blue must be between 0 and 255" }
            require(alpha in 0..255) { "alpha must be between 0 and 255" }

            shadowColor = listOf(red / 255f, green / 255f, blue / 255f, alpha / 255f)
        }

        abstract fun build(): TextComponent
    }
}

object TextComponentAsStringSerializer : AnyToStringJsonSerializer<TextComponent>(
    TextComponent.serializer()
)

object TextComponentAsNbtSerializer : AnyToNbtSerializer<TextComponent>(TextComponent.serializer())

typealias TextComponentAsString = @Serializable(TextComponentAsStringSerializer::class) TextComponent
typealias TextComponentAsNbt = @Serializable(TextComponentAsNbtSerializer::class) TextComponent
