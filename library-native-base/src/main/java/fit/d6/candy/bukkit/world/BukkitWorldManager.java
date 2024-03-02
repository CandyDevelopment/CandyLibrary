package fit.d6.candy.bukkit.world;

import fit.d6.candy.api.CandyLibrary;
import fit.d6.candy.api.world.*;
import fit.d6.candy.bukkit.exception.UnderDevelopmentException;
import fit.d6.candy.bukkit.nms.NmsAccessor;
import org.bukkit.NamespacedKey;
import org.jetbrains.annotations.NotNull;

public class BukkitWorldManager implements WorldManager {

    @Override
    public @NotNull Environment getEnvironmentOverworld() {
        if (!CandyLibrary.version().isWorldEnvironemtnSupport())
            throw new UnderDevelopmentException("Still under development!");
        return BukkitEnvironment.OVERWORLD;
    }

    @Override
    public @NotNull Environment getEnvironmentNether() {
        if (!CandyLibrary.version().isWorldEnvironemtnSupport())
            throw new UnderDevelopmentException("Still under development!");
        return BukkitEnvironment.NETHER;
    }

    @Override
    public @NotNull Environment getEnvironmentTheEnd() {
        if (!CandyLibrary.version().isWorldEnvironemtnSupport())
            throw new UnderDevelopmentException("Still under development!");
        return BukkitEnvironment.THE_END;
    }

    @Override
    public @NotNull EnvironmentBuilder environmentBuilder(@NotNull String name) {
        if (!CandyLibrary.version().isWorldEnvironemtnSupport())
            throw new UnderDevelopmentException("Still under development!");
        return this.environmentBuilder(new NamespacedKey("candy", name));
    }

    @Override
    public @NotNull EnvironmentBuilder environmentBuilder(@NotNull NamespacedKey key) {
        if (!CandyLibrary.version().isWorldEnvironemtnSupport())
            throw new UnderDevelopmentException("Still under development!");
        return new BukkitEnvironmentBuilder(key);
    }

    @Override
    public @NotNull EnvironmentBuilder copyEnvironmentBuilder(@NotNull Environment environment, @NotNull String name) {
        if (!CandyLibrary.version().isWorldEnvironemtnSupport())
            throw new UnderDevelopmentException("Still under development!");
        return NmsAccessor.getAccessor().copyEnvironment((BukkitEnvironment) environment, (BukkitEnvironmentBuilder) this.environmentBuilder(name));
    }

    @Override
    public @NotNull EnvironmentBuilder copyEnvironmentBuilder(@NotNull Environment environment, @NotNull NamespacedKey key) {
        if (!CandyLibrary.version().isWorldEnvironemtnSupport())
            throw new UnderDevelopmentException("Still under development!");
        return NmsAccessor.getAccessor().copyEnvironment((BukkitEnvironment) environment, (BukkitEnvironmentBuilder) this.environmentBuilder(key));
    }

    @Override
    public @NotNull Environment registerEnvironment(@NotNull EnvironmentBuilder builder) {
        if (!CandyLibrary.version().isWorldEnvironemtnSupport())
            throw new UnderDevelopmentException("Still under development!");
        return NmsAccessor.getAccessor().registerEnvironment((BukkitEnvironmentBuilder) builder);
    }

    @Override
    public @NotNull FlatSettings flatSettings() {
        return new BukkitFlatSettings();
    }

    @Override
    public @NotNull WorldInitializer create(@NotNull String name) {
        return this.create(NamespacedKey.minecraft(name));
    }

    @Override
    public @NotNull WorldInitializer create(@NotNull NamespacedKey key) {
        return new BukkitWorldInitializer(key);
    }

}
