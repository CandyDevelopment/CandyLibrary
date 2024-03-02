package fit.d6.candy.bukkit.gui;

import fit.d6.candy.api.gui.GuiAudience;
import fit.d6.candy.api.gui.MovingItemListener;
import fit.d6.candy.api.gui.anvil.AnvilGui;
import fit.d6.candy.api.gui.anvil.AnvilGuiRenderer;
import fit.d6.candy.api.gui.anvil.AnvilGuiScene;
import fit.d6.candy.api.gui.anvil.AnvilSlot;
import fit.d6.candy.api.gui.slot.Slot;
import fit.d6.candy.api.scheduler.SchedulerService;
import fit.d6.candy.bukkit.exception.GuiException;
import fit.d6.candy.bukkit.nms.FakeAnvil;
import fit.d6.candy.bukkit.nms.NmsAccessor;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

public class BukkitAnvilGuiScene extends BukkitGuiScene<AnvilGui, AnvilGuiRenderer, AnvilGuiScene> implements AnvilGuiScene, Listener {

    private final Plugin plugin;
    private final BukkitAnvilGui gui;
    private final Map<AnvilSlot, BukkitSlot> slots;
    private Component title;

    private final Inventory inventory;

    private final BukkitGuiAudience bukkitAudience;
    private boolean isDropped = false;

    final FakeAnvil fakeAnvil;

    private final LinkedList<BukkitAnvilGuiRenderer> renderers = new LinkedList<>();

    private int rendererIndex = 0;

    public BukkitAnvilGuiScene(Plugin plugin, BukkitAnvilGui gui, Map<AnvilSlot, BukkitSlot> slots, Component title, GuiAudience audience, BukkitAnvilGuiRenderer renderer) {
        super(audience);
        this.plugin = plugin;
        this.gui = gui;
        this.slots = new HashMap<>(slots);
        this.title = title;

        this.bukkitAudience = (BukkitGuiAudience) audience;

        this.fakeAnvil = NmsAccessor.getAccessor().createAnvil(this, this.bukkitAudience.getPlayer(), this.title);
        this.inventory = fakeAnvil.getBukkitView().getTopInventory();

        this.render();

        Bukkit.getPluginManager().registerEvents(this, this.plugin);

        this.bukkitAudience.setScene(this);

        NmsAccessor.getAccessor().openAnvil(this.bukkitAudience.getPlayer(), this.fakeAnvil, this.title);

        this.renderers.add(renderer);
    }

    public Map<AnvilSlot, BukkitSlot> getSlots() {
        return new HashMap<>(slots);
    }

    private void render() {
        if (this.slots.containsKey(AnvilSlot.LEFT_INPUT)) {
            org.bukkit.inventory.ItemStack itemStack = convert(this.slots.get(AnvilSlot.LEFT_INPUT));
            if (itemStack != null) {
                this.inventory.setItem(0, itemStack);
            }
        }
        if (this.slots.containsKey(AnvilSlot.RIGHT_INPUT)) {
            org.bukkit.inventory.ItemStack itemStack = convert(this.slots.get(AnvilSlot.RIGHT_INPUT));
            if (itemStack != null) {
                this.inventory.setItem(1, itemStack);
            }
        }
        if (this.slots.containsKey(AnvilSlot.RESULT)) {
            org.bukkit.inventory.ItemStack itemStack = convert(this.slots.get(AnvilSlot.RESULT));
            if (itemStack != null) {
                this.inventory.setItem(2, itemStack);
            }
        }
    }

    private org.bukkit.inventory.ItemStack convert(BukkitSlot slot) {
        if (slot.getImage() == null)
            return null;
        BukkitAnvilGuiContext context = new BukkitAnvilGuiContext(this.getAudience(), this);
        BukkitItemBuilder builder = new BukkitItemBuilder();
        slot.getImage().render(context, builder);
        return builder.build();
    }

    @Override
    public @NotNull Inventory asBukkit() {
        return this.inventory;
    }

    @Override
    public @NotNull AnvilGuiRenderer refresh(boolean keepPrevious) {
        if (this.isDropped)
            throw new GuiException("Trying to refresh a dropped scene");
        return new BukkitAnvilGuiRenderer(this.plugin, this.gui, keepPrevious ? this.slots : this.cast(this.gui.getInitialization()), keepPrevious ? this.title : this.gui.getDefaultTitle(), this.bukkitAudience, this);
    }

    @Override
    public @NotNull AnvilGuiRenderer back() {
        if (!this.hasPrevious())
            throw new GuiException("This gui has no previous renderer");
        this.rendererIndex -= 1;
        return this.renderers.get(rendererIndex);
    }

    @Override
    public @NotNull AnvilGuiRenderer forward() {
        if (!this.hasNext())
            throw new GuiException("This gui has no previous renderer");
        this.rendererIndex += 1;
        return this.renderers.get(rendererIndex);
    }

    @Override
    public boolean hasPrevious() {
        return this.rendererIndex > 0;
    }

    @Override
    public boolean hasNext() {
        return this.rendererIndex < this.renderers.size() - 1;
    }

    @Override
    public boolean isDropped() {
        return this.isDropped;
    }

    @EventHandler
    public void click(InventoryClickEvent event) {
        if (!(event.getWhoClicked() instanceof Player player))
            return;
        if (event.getView() != this.fakeAnvil.getBukkitView())
            return;
        Inventory topInventory = event.getView().getTopInventory();
        Inventory clickedInventory = event.getClickedInventory();
        if (clickedInventory == null)
            return;
        if (event.getAction() == InventoryAction.MOVE_TO_OTHER_INVENTORY && clickedInventory == event.getView().getBottomInventory()) {
            event.setCancelled(true);
            BukkitAnvilGuiClickContext context = new BukkitAnvilGuiClickContext(this.bukkitAudience, this, event.getAction(), event.getClick());
            ItemStack itemStack = event.getView().getBottomInventory().getItem(event.getRawSlot());
            if (itemStack == null)
                itemStack = ItemStack.empty();
            ItemStack finalItemStack = itemStack;
            SchedulerService.getService().getScheduler(this.plugin, player)
                    .runTaskLater(scheduledTask -> {
                        for (MovingItemListener movingItemListener : this.gui.listMovingItemListeners()) {
                            movingItemListener.moving(context, finalItemStack);
                        }
                    }, 1L); // Run task later to prevent the cursor item doesn't be updated
            return;
        }
        if (clickedInventory != topInventory)
            return;

        event.setCancelled(true);

        int rawSlot = event.getRawSlot();

        if (rawSlot > 2)
            return;
        if (rawSlot < 0)
            return;

        AnvilSlot anvilSlot = rawSlot == 0 ? AnvilSlot.LEFT_INPUT : rawSlot == 1 ? AnvilSlot.RIGHT_INPUT : AnvilSlot.RESULT;

        if (!this.slots.containsKey(anvilSlot))
            return;

        BukkitSlot slot = this.slots.get(anvilSlot);

        if (slot.getClicker() == null)
            return;

        BukkitAnvilGuiClickContext context = new BukkitAnvilGuiClickContext(this.bukkitAudience, this, event.getAction(), event.getClick());

        SchedulerService.getService().getScheduler(this.plugin, player)
                .runTaskLater(scheduledTask -> {
                    slot.getClicker().trigger(context);
                }, 1L); // Run task later to prevent the cursor item doesn't be updated
    }

    @EventHandler
    public void close(InventoryCloseEvent event) {
        if (!(event.getPlayer() instanceof Player))
            return;
        if (event.getView().getTopInventory() != this.inventory)
            return;
        if (this.bukkitAudience.getCurrentScene() == this)
            this.bukkitAudience.setScene(null);

        this.isDropped = true;
        HandlerList.unregisterAll(this);

        BukkitAnvilGuiContext context = new BukkitAnvilGuiContext(this.getAudience(), this);
        this.gui.listCloseListeners().forEach(listener -> {
            try {
                listener.close(context);
            } catch (Exception e) {
                plugin.getSLF4JLogger().error("Error occurs when triggering close listener", e);
            }
        });
    }

    @EventHandler
    public void quit(PlayerQuitEvent event) {
        if (event.getPlayer().getUniqueId().equals(this.getAudience().getUniqueId())) {
            HandlerList.unregisterAll(this);
            this.isDropped = true;
        }
    }

    public void update(BukkitAnvilGuiRenderer renderer, Map<AnvilSlot, BukkitSlot> newSlots, Component title) {
        this.title = title;

        this.slots.clear();
        this.slots.putAll(newSlots);

        this.inventory.clear();

        NmsAccessor.getAccessor().setTitle(this.bukkitAudience.getPlayer(), this.inventory, title);

        this.render();

        if (this.renderers.contains(renderer))
            return;

        this.renderers.removeAll(new LinkedList<>(this.renderers.subList(this.rendererIndex + 1, this.renderers.size())));
        this.renderers.add(renderer);
        this.rendererIndex += 1;
    }

    private Map<AnvilSlot, BukkitSlot> cast(Map<AnvilSlot, Slot> initialization) {
        Map<AnvilSlot, BukkitSlot> newMap = new HashMap<>();
        initialization.forEach((anvilSlot, slot) -> {
            newMap.put(anvilSlot, (BukkitSlot) slot);
        });
        return newMap;
    }

}