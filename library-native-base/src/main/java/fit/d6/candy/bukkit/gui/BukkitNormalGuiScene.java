package fit.d6.candy.bukkit.gui;

import fit.d6.candy.api.gui.GuiAudience;
import fit.d6.candy.api.gui.MovingItemListener;
import fit.d6.candy.api.gui.normal.NormalGui;
import fit.d6.candy.api.gui.normal.NormalGuiRenderer;
import fit.d6.candy.api.gui.normal.NormalGuiScene;
import fit.d6.candy.api.gui.slot.Slot;
import fit.d6.candy.api.scheduler.SchedulerService;
import fit.d6.candy.bukkit.exception.GuiException;
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
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

public class BukkitNormalGuiScene extends BukkitGuiScene<NormalGui, NormalGuiRenderer, NormalGuiScene> implements NormalGuiScene, Listener, InventoryHolder {

    private final Plugin plugin;
    private final BukkitNormalGui gui;
    private final int lines;
    private final Map<Integer, BukkitSlot> slots;
    private Component title;

    private final Inventory inventory;

    private final BukkitGuiAudience bukkitAudience;
    private boolean isDropped = false;

    private final LinkedList<BukkitNormalGuiRenderer> renderers = new LinkedList<>();

    private int rendererIndex = 0;

    public BukkitNormalGuiScene(Plugin plugin, BukkitNormalGui gui, int lines, Map<Integer, BukkitSlot> initialization, Component defaultTitle, GuiAudience audience, BukkitNormalGuiRenderer renderer) {
        super(audience);
        this.plugin = plugin;
        this.gui = gui;
        this.lines = lines;
        this.title = defaultTitle;
        this.slots = new HashMap<>(initialization);

        this.bukkitAudience = (BukkitGuiAudience) audience;

        this.inventory = defaultTitle == null ? Bukkit.createInventory(this, lines * 9) : Bukkit.createInventory(this, lines * 9, defaultTitle);

        this.render();

        Bukkit.getPluginManager().registerEvents(this, this.plugin);

        this.bukkitAudience.setScene(this);
        bukkitAudience.getPlayer().openInventory(this.inventory);

        this.renderers.add(renderer);
    }

    private void render() {
        this.slots.forEach((integer, bukkitSlot) -> {
            if (bukkitSlot.getImage() != null) {
                BukkitItemBuilder itemBuilder = new BukkitItemBuilder();
                bukkitSlot.getImage().render(new BukkitNormalGuiContext(this, this.getAudience()), itemBuilder);
                this.inventory.setItem(integer, itemBuilder.build());
            }
        });
    }

    void update(BukkitNormalGuiRenderer renderer, Map<Integer, BukkitSlot> newSlots, Component title) {
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

    @Override
    public @NotNull Inventory asBukkit() {
        return this.inventory;
    }

    @Override
    public @NotNull NormalGuiRenderer refresh(boolean keepPrevious) {
        if (this.isDropped)
            throw new GuiException("Trying to refresh a dropped scene");
        return new BukkitNormalGuiRenderer(this.plugin, this.gui, this.lines, keepPrevious ? new HashMap<>(this.slots) : this.cast(this.gui.getInitialization()), keepPrevious ? this.title : this.gui.getDefaultTitle(), this.getAudience(), this);
    }

    @Override
    public @NotNull NormalGuiRenderer back() {
        if (!this.hasPrevious())
            throw new GuiException("This gui has no previous renderer");
        this.rendererIndex -= 1;
        return this.renderers.get(rendererIndex);
    }

    @Override
    public @NotNull NormalGuiRenderer forward() {
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

    @NotNull
    @Override
    public Inventory getInventory() {
        return inventory;
    }

    @EventHandler
    public void click(InventoryClickEvent event) {
        if (!(event.getWhoClicked() instanceof Player player))
            return;
        if (event.getView().getTopInventory().getHolder() != this)
            return;
        Inventory topInventory = event.getView().getTopInventory();
        Inventory clickedInventory = event.getClickedInventory();
        if (clickedInventory == null)
            return;
        if (event.getAction() == InventoryAction.MOVE_TO_OTHER_INVENTORY && clickedInventory == event.getView().getBottomInventory()) {
            event.setCancelled(true);
            BukkitNormalGuiClickContext context = new BukkitNormalGuiClickContext(this, this.bukkitAudience, event.getAction(), event.getClick());
            ItemStack itemStack = event.getView().getBottomInventory().getItem(event.getRawSlot());
            if (itemStack == null)
                itemStack = ItemStack.empty();
            ItemStack finalItemStack = itemStack;

            SchedulerService.getService().getScheduler(this.plugin, player).runTaskLater(scheduledTask -> {
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

        if (!this.slots.containsKey(rawSlot))
            return;

        BukkitSlot slot = this.slots.get(rawSlot);

        if (slot.getClicker() == null)
            return;

        BukkitNormalGuiClickContext context = new BukkitNormalGuiClickContext(this, this.bukkitAudience, event.getAction(), event.getClick());

        SchedulerService.getService().getScheduler(this.plugin, player).runTaskLater(scheduledTask -> {
            slot.getClicker().trigger(context);
        }, 1L); // Run task later to prevent the cursor item doesn't be updated
    }

    @EventHandler
    public void close(InventoryCloseEvent event) {
        if (!(event.getPlayer() instanceof Player))
            return;
        if (event.getView().getTopInventory().getHolder() != this)
            return;
        if (this.bukkitAudience.getCurrentScene() == this)
            this.bukkitAudience.setScene(null);

        this.isDropped = true;
        HandlerList.unregisterAll(this);

        BukkitNormalGuiContext context = new BukkitNormalGuiContext(this, this.getAudience());
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

    private Map<Integer, BukkitSlot> cast(Map<Integer, Slot> initialization) {
        Map<Integer, BukkitSlot> newMap = new HashMap<>();
        initialization.forEach((integer, slot) -> {
            newMap.put(integer, (BukkitSlot) slot);
        });
        return newMap;
    }

}
