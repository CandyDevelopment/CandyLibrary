package fit.d6.candy.dsl

import fit.d6.candy.api.gui.GuiAudience
import fit.d6.candy.api.gui.GuiService
import fit.d6.candy.api.gui.anvil.*
import fit.d6.candy.api.gui.item.ItemBuilder
import fit.d6.candy.api.gui.normal.*
import fit.d6.candy.api.gui.slot.SlotBuilder
import org.bukkit.entity.Player
import org.bukkit.plugin.Plugin

fun Player.guiAudience(): GuiAudience {
    return GuiAudience.getAudience(this)
}

fun NormalGui(plugin: Plugin, lines: Int, content: NormalGuiInitializer.() -> Unit): NormalGui {
    return GuiService.getService()
        .guiManager
        .normal(lines)
        .create(plugin)
        .apply(content)
        .initialize()
}

fun AnvilGui(plugin: Plugin, content: AnvilGuiInitializer.() -> Unit): AnvilGui {
    return GuiService.getService()
        .guiManager
        .anvil()
        .create(plugin)
        .apply(content)
        .initialize()
}

fun Renderer(normalGui: NormalGui, audience: GuiAudience, content: NormalGuiRenderer.() -> Unit): NormalGuiScene {
    return normalGui.prepare(audience)
        .apply(content)
        .render()
}

fun NormalGuiInitializer.Slot(slot: Int, content: NormalSlotBuilder.() -> Unit) {
    val normalSlotBuilder = NormalSlotBuilder()
    normalSlotBuilder.apply(content)
    this.slot(slot) {
        normalSlotBuilder.resolve(it)
    }
}

fun NormalGuiRenderer.Slot(slot: Int, content: NormalSlotBuilder.() -> Unit) {
    val normalSlotBuilder = NormalSlotBuilder()
    normalSlotBuilder.apply(content)
    this.slot(slot) {
        normalSlotBuilder.resolve(it)
    }
}

fun AnvilGuiInitializer.Slot(slot: AnvilSlot, content: AnvilSlotBuilder.() -> Unit) {
    val anvilSlotBuilder = AnvilSlotBuilder()
    anvilSlotBuilder.apply(content)
    this.slot(slot) {
        anvilSlotBuilder.resolve(it)
    }
}

fun AnvilGuiRenderer.Slot(slot: AnvilSlot, content: AnvilSlotBuilder.() -> Unit) {
    val anvilSlotBuilder = AnvilSlotBuilder()
    anvilSlotBuilder.apply(content)
    this.slot(slot) {
        anvilSlotBuilder.resolve(it)
    }
}

class NormalSlotBuilder internal constructor() {

    private var image: (ItemBuilder.(NormalGuiContext) -> Unit)? = null
    private var clicker: ((NormalGuiClickContext) -> Unit)? = null

    fun Image(content: ItemBuilder.(context: NormalGuiContext) -> Unit) {
        this.image = content;
    }

    fun Clicker(content: (context: NormalGuiClickContext) -> Unit) {
        this.clicker = content
    }

    internal fun resolve(slotBuilder: SlotBuilder) {
        if (this.image != null) {
            slotBuilder.image { context, itemBuilder ->
                this.image!!(itemBuilder, context as NormalGuiContext)
            }
        }
        if (this.clicker != null) {
            slotBuilder.clicker { context ->
                this.clicker!!(context as NormalGuiClickContext)
            }
        }
    }

}

class AnvilSlotBuilder internal constructor() {

    private var image: (ItemBuilder.(AnvilGuiContext) -> Unit)? = null
    private var clicker: ((AnvilGuiClickContext) -> Unit)? = null

    fun Image(content: ItemBuilder.(context: AnvilGuiContext) -> Unit) {
        this.image = content;
    }

    fun Clicker(content: (context: AnvilGuiClickContext) -> Unit) {
        this.clicker = content
    }

    internal fun resolve(slotBuilder: SlotBuilder) {
        if (this.image != null) {
            slotBuilder.image { context, itemBuilder ->
                this.image!!(itemBuilder, context as AnvilGuiContext)
            }
        }
        if (this.clicker != null) {
            slotBuilder.clicker { context ->
                this.clicker!!(context as AnvilGuiClickContext)
            }
        }
    }

}