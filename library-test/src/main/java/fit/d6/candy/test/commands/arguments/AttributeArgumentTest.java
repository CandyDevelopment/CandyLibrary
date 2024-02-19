package fit.d6.candy.test.commands.arguments;

import fit.d6.candy.api.command.ArgumentManager;
import fit.d6.candy.api.command.CommandBuilder;
import fit.d6.candy.api.command.CommandManager;
import fit.d6.candy.api.command.CommandService;
import org.bukkit.Material;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.UUID;

public class AttributeArgumentTest {

    public static void register(CommandBuilder builder) {
        CommandManager commandManager = CommandService.getService().getCommandManager();
        ArgumentManager argumentManager = CommandService.getService().getArgumentManager();
        builder.then(
                commandManager.createCommand("attribute")
                        .then(
                                commandManager.createCommand("value", argumentManager.attribute())
                                        .executesPlayer((context, argument) -> {
                                            ItemStack itemStack = new ItemStack(Material.DIAMOND);
                                            ItemMeta itemMeta = itemStack.getItemMeta();
                                            if (itemMeta != null) {
                                                itemMeta.addAttributeModifier(argument.getAttribute("value"), new AttributeModifier(
                                                        UUID.randomUUID(),
                                                        "candylibrarytest",
                                                        100,
                                                        AttributeModifier.Operation.ADD_NUMBER,
                                                        EquipmentSlot.HAND
                                                ));
                                                itemStack.setItemMeta(itemMeta);
                                            }
                                            context.getPlayer().getInventory().addItem(itemStack);
                                            return 1;
                                        })
                        )
        );
    }

}
