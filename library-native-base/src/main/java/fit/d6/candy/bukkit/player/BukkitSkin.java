package fit.d6.candy.bukkit.player;

import fit.d6.candy.api.player.Skin;
import org.jetbrains.annotations.NotNull;

public class BukkitSkin implements Skin {

    private final String textures;
    private final String signature;

    public BukkitSkin(String textures, String signature) {
        this.textures = textures;
        this.signature = signature;
    }

    @Override
    public @NotNull String getTextures() {
        return this.textures;
    }

    @Override
    public @NotNull String getSignature() {
        return this.signature;
    }

}
