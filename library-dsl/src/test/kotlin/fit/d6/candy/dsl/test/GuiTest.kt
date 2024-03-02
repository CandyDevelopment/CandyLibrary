package fit.d6.candy.dsl.test

import fit.d6.candy.bukkit.dsl.NormalGui
import fit.d6.candy.bukkit.dsl.Renderer
import fit.d6.candy.bukkit.dsl.Slot
import fit.d6.candy.bukkit.dsl.guiAudience
import net.kyori.adventure.text.Component
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.plugin.Plugin

fun show(plugin: Plugin, player: Player) {
    val gui = NormalGui(plugin, 5) {

        Slot(0) {

            Image {
                this.type(Material.DIAMOND)
            }

            Clicker {
                it.audience.asBukkit().sendMessage("Hello, world!")
            }

        }

    }

    Renderer(gui, player.guiAudience()) {

        Slot(4) {

            Image {
                this.type(Material.PAPER)
                displayName(Component.text("Hello, test!"))
            }

        }

    }

}