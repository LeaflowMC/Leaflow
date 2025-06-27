package io.github.leaflowmc.leaflow.text.component

import io.github.leaflowmc.leaflow.text.ClickEvent
import io.github.leaflowmc.leaflow.text.HoverEvent
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
@SerialName("text")
class PlainTextComponent(
    val text: String,
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
        inline fun builder(block: Builder.() -> Unit): PlainTextComponent {
            return Builder().apply(block).build()
        }
    }

    class Builder : TextComponent.Builder() {
        var text: String? = null

        override fun build(): PlainTextComponent {
            return PlainTextComponent(
                requireNotNull(text) { "text must be set" },
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