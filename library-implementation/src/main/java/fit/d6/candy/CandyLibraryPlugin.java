package fit.d6.candy;

import fit.d6.candy.api.CandyLibrary;
import fit.d6.candy.api.CandyVersion;
import fit.d6.candy.api.Service;
import fit.d6.candy.api.collection.CollectionService;
import fit.d6.candy.api.command.CommandService;
import fit.d6.candy.api.configuration.ConfigurationService;
import fit.d6.candy.api.configuration.ConfigurationType;
import fit.d6.candy.api.database.DatabaseService;
import fit.d6.candy.api.event.EventService;
import fit.d6.candy.api.gui.GuiService;
import fit.d6.candy.api.item.ItemService;
import fit.d6.candy.api.messenger.MessengerService;
import fit.d6.candy.api.nbt.NbtService;
import fit.d6.candy.api.player.PlayerService;
import fit.d6.candy.api.protocol.ProtocolService;
import fit.d6.candy.api.scheduler.SchedulerService;
import fit.d6.candy.api.time.TimeService;
import fit.d6.candy.api.visual.VisualService;
import fit.d6.candy.api.world.WorldService;
import fit.d6.candy.collection.BukkitCollectionService;
import fit.d6.candy.command.BukkitCommandService;
import fit.d6.candy.configuration.BukkitConfigurationService;
import fit.d6.candy.database.BukkitDatabaseService;
import fit.d6.candy.event.BukkitEventService;
import fit.d6.candy.exception.ServiceNotExistsException;
import fit.d6.candy.gui.BukkitGuiService;
import fit.d6.candy.item.BukkitItemService;
import fit.d6.candy.messenger.BukkitMessengerService;
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
import fit.d6.candy.scheduler.BukkitSchedulerService;
import fit.d6.candy.time.BukkitTimeService;
import fit.d6.candy.util.Ref;
import fit.d6.candy.visual.BukkitVisualService;
import fit.d6.candy.world.BukkitWorldService;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class CandyLibraryPlugin extends JavaPlugin implements CandyLibrary {

    private final Map<Class<?>, Service> services = new HashMap<>();

    private boolean isUnsupported = false;
    private CandyVersion candyVersion;

    private NmsAccessor accessor;

    @Override
    public void onLoad() {
        String version = Bukkit.getBukkitVersion();
        version = version.split("-")[0].split("\\.")[1];
        if (Integer.parseInt(version) >= 21) { // For 1.21+ paper removed craft bukkit relocation
            candyVersion = CandyVersion.V1_21;
        } else {
            if (Ref.getObcVersion().equals("1_20_R3")) {
                this.accessor = new NmsAccessorV1_20_R3();
                candyVersion = CandyVersion.V1_20_R3;
            } else if (Ref.getObcVersion().equals("1_20_R2")) {
                this.accessor = new NmsAccessorV1_20_R2();
                candyVersion = CandyVersion.V1_20_R2;
            } else if (Ref.getObcVersion().equals("1_20_R1")) {
                this.accessor = new NmsAccessorV1_20_R1();
                candyVersion = CandyVersion.V1_20_R1;
            } else if (Ref.getObcVersion().equals("1_19_R3")) {
                this.accessor = new NmsAccessorV1_19_R3();
                candyVersion = CandyVersion.V1_19_R3;
            } else if (Ref.getObcVersion().equals("1_19_R2")) {
                this.accessor = new NmsAccessorV1_19_R2();
                candyVersion = CandyVersion.V1_19_R2;
            } else if (Ref.getObcVersion().equals("1_19_R1")) {
                this.accessor = new NmsAccessorV1_19_R1();
                candyVersion = CandyVersion.V1_19_R1;
            } else if (Ref.getObcVersion().equals("1_18_R2")) {
                this.accessor = new NmsAccessorV1_18_R2();
                candyVersion = CandyVersion.V1_18_R2;
            } else if (Ref.getObcVersion().equals("1_18_R1")) {
                this.accessor = new NmsAccessorV1_18_R1();
                candyVersion = CandyVersion.V1_18_R1;
            } else if (Ref.getObcVersion().equals("1_17_R1")) {
                this.accessor = new NmsAccessorV1_17_R1();
                candyVersion = CandyVersion.V1_17_R1;
            } else {
                isUnsupported = true;
                return;
            }
        }

        if (!this.getDataFolder().isDirectory())
            this.getDataFolder().mkdirs();

        File configFile = new File(this.getDataFolder(), "config.yml");

        if (!configFile.isFile())
            new BukkitConfigurationService().save(new CandyLibraryConfiguration(), configFile, ConfigurationType.YAML);

        CandyLibraryConfiguration configuration = (CandyLibraryConfiguration) new BukkitConfigurationService().load(CandyLibraryConfiguration.class, configFile, ConfigurationType.YAML);
        new BukkitConfigurationService().save(configuration, configFile, ConfigurationType.YAML);

        if (configuration.gui)
            this.services.put(GuiService.class, new BukkitGuiService());
        if (configuration.command)
            this.services.put(CommandService.class, new BukkitCommandService());
        if (configuration.configuration)
            this.services.put(ConfigurationService.class, new BukkitConfigurationService());
        if (configuration.nbt)
            this.services.put(NbtService.class, new BukkitNbtService());
        if (configuration.visual)
            this.services.put(VisualService.class, new BukkitVisualService());
        if (configuration.player)
            this.services.put(PlayerService.class, new BukkitPlayerService());
        if (configuration.protocol)
            this.services.put(ProtocolService.class, new BukkitProtocolService());
        if (configuration.collection)
            this.services.put(CollectionService.class, new BukkitCollectionService());
        if (configuration.database)
            this.services.put(DatabaseService.class, new BukkitDatabaseService());
        if (configuration.item)
            this.services.put(ItemService.class, new BukkitItemService());
        if (configuration.time)
            this.services.put(TimeService.class, new BukkitTimeService());
        if (configuration.scheduler)
            this.services.put(SchedulerService.class, new BukkitSchedulerService(this.candyVersion.isFoliaSchedulerSupport()));
        if (configuration.world)
            this.services.put(WorldService.class, new BukkitWorldService());
        if (configuration.event)
            this.services.put(EventService.class, new BukkitEventService());
        if (configuration.messenger)
            this.services.put(MessengerService.class, new BukkitMessengerService());
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

    @Override
    public <S extends Service> boolean isServiceConfigured(@NotNull Class<S> clazz) {
        return this.services.containsKey(clazz);
    }

    @Override
    public @NotNull CandyVersion getVersion() {
        return this.candyVersion;
    }

    public @NotNull NmsAccessor getNmsAccessor() {
        return this.accessor;
    }

}
