package fit.d6.candy.nms.v1_20_R1;

import fit.d6.candy.api.gui.anvil.AnvilSlot;
import fit.d6.candy.gui.BukkitAnvilGuiContext;
import fit.d6.candy.gui.BukkitAnvilGuiScene;
import fit.d6.candy.gui.BukkitItemBuilder;
import fit.d6.candy.gui.BukkitSlot;
import fit.d6.candy.nms.FakeAnvil;
import io.papermc.paper.adventure.PaperAdventure;
import net.kyori.adventure.text.Component;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.Container;
import net.minecraft.world.inventory.AnvilMenu;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.item.ItemStack;
import org.bukkit.craftbukkit.v1_20_R1.CraftWorld;
import org.bukkit.craftbukkit.v1_20_R1.entity.CraftPlayer;
import org.bukkit.craftbukkit.v1_20_R1.inventory.CraftItemStack;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class BukkitFakeAnvil extends AnvilMenu implements FakeAnvil {

    private static ServerPlayer player(Player player) {
        return ((CraftPlayer) player).getHandle();
    }

    private final BukkitAnvilGuiScene scene;

    public BukkitFakeAnvil(BukkitAnvilGuiScene scene, Player player, Component component) {
        super(player(player).nextContainerCounter(), player(player).getInventory(), ContainerLevelAccess.create(((CraftWorld) player.getWorld()).getHandle(), new BlockPos(0, 0, 0)));

        this.scene = scene;

        this.checkReachable = false;
        this.setTitle(PaperAdventure.asVanilla(component));
    }

    public BukkitAnvilGuiScene getScene() {
        return scene;
    }

    @Override
    public void createResult() {
        this.getSlot(2).set(ItemStack.EMPTY);


        if (scene.getSlots().containsKey(AnvilSlot.RESULT)) {

            BukkitSlot slot = scene.getSlots().get(AnvilSlot.RESULT);
            if (slot.getImage() != null) {
                String name = this.itemName;

                BukkitAnvilGuiContext context = new BukkitAnvilGuiContext(this.scene.getAudience(), this.scene);
                BukkitItemBuilder builder = new BukkitItemBuilder();
                slot.getImage().render(context, builder);
                this.getSlot(2).set(CraftItemStack.asNMSCopy(builder.build()));

                this.itemName = name;
            }

        }

        this.cost.set(0);

        this.sendAllDataToRemote();
        this.broadcastChanges();
    }

    @Override
    public void removed(net.minecraft.world.entity.player.@NotNull Player player) {
    }

    @Override
    protected void clearContainer(net.minecraft.world.entity.player.Player player, Container inventory) {
    }

    @Override
    public @NotNull String getItemName() {
        return this.itemName;
    }

    @Override
    public int getContainerId() {
        return this.containerId;
    }

}
