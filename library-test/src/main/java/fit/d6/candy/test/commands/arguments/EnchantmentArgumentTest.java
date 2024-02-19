package fit.d6.candy.test.commands.arguments;

import fit.d6.candy.api.command.ArgumentManager;
import fit.d6.candy.api.command.CommandBuilder;
import fit.d6.candy.api.command.CommandManager;
import fit.d6.candy.api.command.CommandService;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class EnchantmentArgumentTest {

    public static void register(CommandBuilder builder) {
        CommandManager commandManager = CommandService.getService().getCommandManager();
        ArgumentManager argumentManager = CommandService.getService().getArgumentManager();
        builder.then(
                commandManager.createCommand("enchantment")
                        .then(
                                commandManager.createCommand("value", argumentManager.enchantment())
                                        .executesPlayer((context, argument) -> {
                                            ItemStack itemStack = new ItemStack(Material.DIAMOND);
                                            ItemMeta itemMeta = itemStack.getItemMeta();
                                            if (itemMeta != null) {
                                                itemMeta.addEnchant(argument.getEnchantment("value"), 1, true);
                                                itemStack.setItemMeta(itemMeta);
                                            }
                                            context.getPlayer().getInventory().addItem(itemStack);
                                            return 1;
                                        })
                        )
        );
    }

}
