package io.github.leaflowmc.leaflow.text.component

import io.github.leaflowmc.leaflow.text.ClickEvent
import io.github.leaflowmc.leaflow.text.HoverEvent
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
@SerialName("translatable")
class TranslatedTextComponent(
    val translate: String,
    val fallback: String? = null,
    val with: List<TextComponent>? = null,
    override val extra: List<TextComponent>? = null,
    override val color: String? = null,
    override val font: String? = null,
    override val bold: Boolean? = null,
    override val italic: Boolean? = null,
    override val underlined: Boolean? = null,
    override val strikethrough: Boolean? = null,
    override val obfuscated: Boolean? = null,
    @SerialName("shadow_color")
    override val shadowColor: List<Float>? = null,
    override val insertion: String? = null,
    @SerialName("click_event")
    override val clickEvent: ClickEvent? = null,
    @SerialName("hover_event")
    override val hoverEvent: HoverEvent? = null
) : TextComponent() {
    companion object {
        inline fun builder(block: Builder.() -> Unit): TranslatedTextComponent {
            return Builder().apply(block).build()
        }
    }

    class Builder : TextComponent.Builder() {
        var translate: String? = null
        var fallback: String? = null
        val with = mutableListOf<TextComponent>()

        override fun build(): TranslatedTextComponent {
            return TranslatedTextComponent(
                requireNotNull(translate) { "translate must be set" },
                fallback,
                if (with.isEmpty()) null else with.toList(),
                if (extra.isEmpty()) null else extra.toList(),
                color,
                font,
                bold,
                italic,
                underlined,
                strikethrough,
                obfuscated,
                shadowColor,
                insertion,
                clickEvent,
                hoverEvent
            )
        }
    }
}