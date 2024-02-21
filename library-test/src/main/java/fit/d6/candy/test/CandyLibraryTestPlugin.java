package fit.d6.candy.test;

import fit.d6.candy.api.CandyLibrary;
import fit.d6.candy.api.command.CommandBuilder;
import fit.d6.candy.api.command.CommandManager;
import fit.d6.candy.api.command.CommandService;
import fit.d6.candy.api.configuration.ConfigurationService;
import fit.d6.candy.api.configuration.ConfigurationType;
import fit.d6.candy.api.protocol.ProtocolService;
import fit.d6.candy.api.protocol.packet.ClientboundPlayerChatPacket;
import fit.d6.candy.api.protocol.packet.PacketType;
import fit.d6.candy.api.world.WorldManager;
import fit.d6.candy.test.commands.arguments.*;
import fit.d6.candy.test.services.GuiServiceTest;
import fit.d6.candy.test.services.ScoreboardServiceTest;
import fit.d6.candy.test.services.TabListServiceTest;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.World;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.concurrent.atomic.AtomicReference;

public class CandyLibraryTestPlugin extends JavaPlugin {

    public final static AtomicReference<World> WORLD = new AtomicReference<>(null);

    @Override
    public void onEnable() {
        CommandService service = CandyLibrary.getLibrary().getService(CommandService.class);
        CommandManager commandManager = service.getCommandManager();

        CommandBuilder candyLibraryCommand = commandManager.createCommand("candylibrary")
                .executes((context, argument) -> {
                    context.getSender().sendMessage("Success");
                    return 1;
                });

        CommandBuilder arguments = commandManager.createCommand("arguments");

        StringArgumentTest.register(arguments);
        BooleanArgumentTest.register(arguments);
        IntegerArgumentTest.register(arguments);
        DoubleArgumentTest.register(arguments);
        LongArgumentTest.register(arguments);
        FloatArgumentTest.register(arguments);
        SingleEntityArgumentTest.register(arguments);
        EntitiesArgumentTest.register(arguments);
        SinglePlayerArgumentTest.register(arguments);
        PlayersArgumentTest.register(arguments);
        AngleArgumentTest.register(arguments);
        ComponentArgumentTest.register(arguments);
        WorldArgumentTest.register(arguments);
        MessageArgumentTest.register(arguments);
        if (Ref.getObcVersion().equals("1_20_R3")) {
            GameModeArgumentTest.register(arguments);
        }
        VectorArgumentTest.register(arguments);
        ParticleArgumentTest.register(arguments);
        BlockArgumentTest.register(arguments);
        ItemArgumentTest.register(arguments);
        EnchantmentArgumentTest.register(arguments);
        if (Ref.getObcVersion().equals("1_20_R3")) {
            EntityTypeArgumentTest.register(arguments);
        }
        SummonableEntityTypeArgumentTest.register(arguments);
        PotionEffectArgumentTest.register(arguments);
        NbtCompoundArgumentTest.register(arguments);
        AttributeArgumentTest.register(arguments);
        BlockPositionArgumentTest.register(arguments);
        RotationArgumentTest.register(arguments);
        DurationArgumentTest.register(arguments);

        CommandBuilder services = commandManager.createCommand("services");

        ScoreboardServiceTest.register(services);
        TabListServiceTest.register(services);
        GuiServiceTest.register(services);

        candyLibraryCommand.then(arguments);
        candyLibraryCommand.then(services);

        commandManager.register("candy", candyLibraryCommand);

        ConfigurationService configurationService = CandyLibrary.getLibrary().getService(ConfigurationService.class);

        if (!this.getDataFolder().exists())
            this.getDataFolder().mkdirs();

        File json = new File(this.getDataFolder(), "test.json");
        File yaml = new File(this.getDataFolder(), "test.yml");
        File toml = new File(this.getDataFolder(), "test.toml");

        ConfigExample example = new ConfigExample();

        example.name = "DeeChael";
        example.age = 18;

        configurationService.save(example, json, ConfigurationType.JSON);
        configurationService.save(example, yaml, ConfigurationType.YAML);
        configurationService.save(example, toml, ConfigurationType.TOML);

        System.out.println("Loading json test");

        ConfigExample jsonExample = (ConfigExample) configurationService.load(ConfigExample.class, json, ConfigurationType.JSON);
        System.out.println(jsonExample.name);
        System.out.println(jsonExample.age);
        System.out.println(jsonExample.values);
        System.out.println(jsonExample.test);

        System.out.println("Loading yaml test");

        ConfigExample yamlExample = (ConfigExample) configurationService.load(ConfigExample.class, yaml, ConfigurationType.YAML);
        System.out.println(yamlExample.name);
        System.out.println(yamlExample.age);
        System.out.println(yamlExample.values);
        System.out.println(yamlExample.test);

        System.out.println("Loading toml test");

        ConfigExample tomlExample = (ConfigExample) configurationService.load(ConfigExample.class, toml, ConfigurationType.TOML);
        System.out.println(tomlExample.name);
        System.out.println(tomlExample.age);
        System.out.println(tomlExample.values);
        System.out.println(tomlExample.test);

        ProtocolService protocolService = CandyLibrary.getLibrary().getService(ProtocolService.class);

        protocolService.getProtocolManager()
                .createListener(this)
                .register(PacketType.CLIENTBOUND_PLAYER_CHAT, ((player, packet) -> {
                    ClientboundPlayerChatPacket clientboundPlayerChatPacket = (ClientboundPlayerChatPacket) packet;
                    clientboundPlayerChatPacket.setContent(Component.text("You can never send out your message, hahaha").color(NamedTextColor.GOLD));
                }));

        System.out.println("Is Folia? " + Ref.isFolia());

        // Actually, if you want to connect these worlds, you should register a listener to listen player use portal event and handle it by yourself

        WorldManager.getManager().create("test_world")
                .initialize(this, world -> {
                    WORLD.set(world.asBukkit());
                    WORLD.get().setAutoSave(true);
                    WORLD.get().setSpawnLocation(0, 100, 0);
                });

        WorldManager.getManager().create("test_world_nether")
                .environment(World.Environment.NETHER)
                .initialize(this, world -> {
                    World bukkitWorld = world.asBukkit();
                    bukkitWorld.setAutoSave(true);
                    bukkitWorld.setSpawnLocation(0, 100, 0);
                });

        WorldManager.getManager().create("test_world_the_end")
                .environment(World.Environment.THE_END)
                .initialize(this, world -> {
                    World bukkitWorld = world.asBukkit();
                    bukkitWorld.setAutoSave(true);
                    bukkitWorld.setSpawnLocation(0, 100, 0);
                });
    }

    public static CandyLibraryTestPlugin getInstance() {
        return JavaPlugin.getPlugin(CandyLibraryTestPlugin.class);
    }

}
