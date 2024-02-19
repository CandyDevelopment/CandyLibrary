package fit.d6.candy;

import fit.d6.candy.api.CandyLibrary;
import fit.d6.candy.api.Service;
import fit.d6.candy.api.collection.CollectionService;
import fit.d6.candy.api.command.CommandService;
import fit.d6.candy.api.configuration.ConfigurationService;
import fit.d6.candy.api.database.DatabaseService;
import fit.d6.candy.api.gui.GuiService;
import fit.d6.candy.api.item.ItemService;
import fit.d6.candy.api.nbt.NbtService;
import fit.d6.candy.api.player.PlayerService;
import fit.d6.candy.api.protocol.ProtocolService;
import fit.d6.candy.api.visual.VisualService;
import fit.d6.candy.collection.BukkitCollectionService;
import fit.d6.candy.command.BukkitCommandService;
import fit.d6.candy.configuration.BukkitConfigurationService;
import fit.d6.candy.database.BukkitDatabaseService;
import fit.d6.candy.exception.ServiceNotExistsException;
import fit.d6.candy.gui.BukkitGuiService;
import fit.d6.candy.item.BukkitItemService;
import fit.d6.candy.nbt.BukkitNbtService;
import fit.d6.candy.nms.NmsAccessor;
import fit.d6.candy.nms.v1_17_R1.NmsAccessorV1_17_R1;
import fit.d6.candy.nms.v1_18_R1.NmsAccessorV1_18_R1;
import fit.d6.candy.nms.v1_18_R2.NmsAccessorV1_18_R2;
import fit.d6.candy.nms.v1_19_R1.NmsAccessorV1_19_R1;
import fit.d6.candy.nms.v1_19_R2.NmsAccessorV1_19_R2;
import fit.d6.candy.nms.v1_19_R3.NmsAccessorV1_19_R3;
import fit.d6.candy.nms.v1_20_R1.NmsAccessorV1_20_R1;
import fit.d6.candy.nms.v1_20_R2.NmsAccessorV1_20_R2;
import fit.d6.candy.nms.v1_20_R3.NmsAccessorV1_20_R3;
import fit.d6.candy.player.BukkitPlayerService;
import fit.d6.candy.protocol.BukkitProtocolService;
import fit.d6.candy.util.Ref;
import fit.d6.candy.visual.BukkitVisualService;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

public class CandyLibraryPlugin extends JavaPlugin implements CandyLibrary {

    private final Map<Class<?>, Service> services = new HashMap<>();

    private boolean isUnsupported = false;

    private NmsAccessor accessor;

    @Override
    public void onLoad() {
        if (Ref.getObcVersion().equals("1_20_R3")) {
            this.accessor = new NmsAccessorV1_20_R3();
        } else if (Ref.getObcVersion().equals("1_20_R2")) {
            this.accessor = new NmsAccessorV1_20_R2();
        } else if (Ref.getObcVersion().equals("1_20_R1")) {
            this.accessor = new NmsAccessorV1_20_R1();
        } else if (Ref.getObcVersion().equals("1_19_R3")) {
            this.accessor = new NmsAccessorV1_19_R3();
        } else if (Ref.getObcVersion().equals("1_19_R2")) {
            this.accessor = new NmsAccessorV1_19_R2();
        } else if (Ref.getObcVersion().equals("1_19_R1")) {
            this.accessor = new NmsAccessorV1_19_R1();
        } else if (Ref.getObcVersion().equals("1_18_R2")) {
            this.accessor = new NmsAccessorV1_18_R2();
        } else if (Ref.getObcVersion().equals("1_18_R1")) {
            this.accessor = new NmsAccessorV1_18_R1();
        } else if (Ref.getObcVersion().equals("1_17_R1")) {
            this.accessor = new NmsAccessorV1_17_R1();
        } else {
            isUnsupported = true;
            return;
        }

        this.services.put(GuiService.class, new BukkitGuiService());
        this.services.put(CommandService.class, new BukkitCommandService());
        this.services.put(ConfigurationService.class, new BukkitConfigurationService());
        this.services.put(NbtService.class, new BukkitNbtService());
        this.services.put(VisualService.class, new BukkitVisualService());
        this.services.put(PlayerService.class, new BukkitPlayerService());
        this.services.put(ProtocolService.class, new BukkitProtocolService());
        this.services.put(CollectionService.class, new BukkitCollectionService());
        this.services.put(DatabaseService.class, new BukkitDatabaseService());
        this.services.put(ItemService.class, new BukkitItemService());
        this.services.put(NmsAccessor.class, this.accessor);

        this.services.values().forEach(service -> {
            if (service instanceof BukkitService bukkitService)
                bukkitService.load(this);
        });
    }

    @Override
    public void onEnable() {
        if (isUnsupported) {
            this.getSLF4JLogger().error("Unsupported server version");
            Bukkit.getPluginManager().disablePlugin(this);
            return;
        }

        this.services.values().forEach(service -> {
            if (service instanceof BukkitService bukkitService)
                bukkitService.enable(this);
        });
    }

    @Override
    public void onDisable() {
        this.services.values().forEach(service -> {
            if (service instanceof BukkitService bukkitService)
                bukkitService.disable(this);
        });
    }

    @SuppressWarnings("unchecked")
    @Override
    public <S extends Service> @NotNull S getService(@NotNull Class<S> clazz) {
        if (!this.services.containsKey(clazz))
            throw new ServiceNotExistsException("The service is not registered or not exists");
        return (S) this.services.get(clazz);
    }

    public @NotNull NmsAccessor getNmsAccessor() {
        return this.accessor;
    }

}
