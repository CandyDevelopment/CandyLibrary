package fit.d6.candy.test.services;

import fit.d6.candy.api.command.CommandBuilder;
import fit.d6.candy.api.command.CommandManager;
import fit.d6.candy.api.command.CommandService;
import fit.d6.candy.api.item.ItemStackBuilder;
import net.kyori.adventure.text.Component;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.potion.PotionType;

import java.util.UUID;

public class ItemServiceTest {

    public static void register(CommandBuilder builder) {
        CommandManager commandManager = CommandService.getService().getCommandManager();

        builder.then(
                commandManager.createCommand("item")
                        .then(
                                commandManager.createCommand("simple")
                                        .executesPlayer((context, argument) -> {

                                            context.getPlayer().getInventory()
                                                    .addItem(
                                                            ItemStackBuilder.of()
                                                                    .type(Material.DIAMOND_SWORD)
                                                                    .displayName(Component.text("AAAA"))
                                                                    .lore(
                                                                            Component.text("first"),
                                                                            Component.text("second")
                                                                    )
                                                                    .enchant(Enchantment.DAMAGE_ALL, 5, true)
                                                                    .attribute(
                                                                            Attribute.GENERIC_ATTACK_DAMAGE,
                                                                            new AttributeModifier(
                                                                                    UUID.randomUUID(),
                                                                                    "candy_test",
                                                                                    233.0d,
                                                                                    AttributeModifier.Operation.ADD_NUMBER,
                                                                                    EquipmentSlot.HAND
                                                                            )
                                                                    )
                                                                    .unbreakable(true)
                                                                    .build()
                                                    );

                                            return 1;
                                        })
                        )
                        .then(
                                commandManager.createCommand("potion")
                                        .executesPlayer((context, argument) -> {

                                            context.getPlayer().getInventory()
                                                    .addItem(
                                                            ItemStackBuilder.of()
                                                                    .type(Material.POTION)
                                                                    .potionColor(Color.LIME)
                                                                    .potionType(PotionType.UNCRAFTABLE)
                                                                    .potionEffect(
                                                                            new PotionEffect(
                                                                                    PotionEffectType.NIGHT_VISION,
                                                                                    100,
                                                                                    0,
                                                                                    false,
                                                                                    false,
                                                                                    true
                                                                            ),
                                                                            true
                                                                    )
                                                                    .potionEffect(
                                                                            new PotionEffect(
                                                                                    PotionEffectType.JUMP,
                                                                                    100,
                                                                                    1,
                                                                                    false,
                                                                                    false,
                                                                                    true
                                                                            ),
                                                                            true
                                                                    )
                                                                    .build()
                                                    );

                                            return 1;
                                        })
                        )
        );
    }

}
