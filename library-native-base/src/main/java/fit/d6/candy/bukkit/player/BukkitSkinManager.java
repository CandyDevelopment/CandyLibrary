package fit.d6.candy.bukkit.player;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import fit.d6.candy.api.player.BuiltinSkins;
import fit.d6.candy.api.player.Skin;
import fit.d6.candy.api.player.SkinManager;
import fit.d6.candy.bukkit.nms.NmsAccessor;
import fit.d6.candy.bukkit.util.StringUtils;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

public class BukkitSkinManager implements SkinManager {

    private final BuiltinSkins builtinSkins = new BukkitBuiltinSkins();

    @Override
    public BuiltinSkins getBuiltinSkins() {
        return builtinSkins;
    }

    @Override
    public @NotNull Skin getSkin(@NotNull String texture, @NotNull String signature) {
        return new BukkitSkin(texture, signature);
    }

    @Override
    public @NotNull List<@NotNull Skin> getSkin(@NotNull Player player) {
        return NmsAccessor.getAccessor().getPlayerSkin(player);
    }

    @Override
    public @NotNull Future<@NotNull List<@NotNull Skin>> getSkin(@NotNull UUID onlinePlayerUuid) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                List<Skin> skins = new ArrayList<>();
                URL url = new URL("https://sessionserver.mojang.com/session/minecraft/profile/" + onlinePlayerUuid + "?unsigned=false");
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                    for (JsonElement element : JsonParser.parseReader(new InputStreamReader(connection.getInputStream())).getAsJsonObject().get("properties").getAsJsonArray()) {
                        JsonObject jsonObject = element.getAsJsonObject();
                        String value = jsonObject.get("value").getAsString();
                        String signature = jsonObject.get("signature").getAsString();
                        skins.add(new BukkitSkin(value, signature));
                    }
                }
                return skins;
            } catch (IOException ignored) {
            }
            return List.of();
        });
    }

    @Override
    public @NotNull Future<@NotNull List<@NotNull Skin>> getSkin(@NotNull String onlinePlayerName) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                URL getIdUrl = new URL("https://api.mojang.com/users/profiles/minecraft/" + onlinePlayerName.toLowerCase());
                HttpURLConnection getIdUrlConnection = (HttpURLConnection) getIdUrl.openConnection();
                if (getIdUrlConnection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                    String id = JsonParser.parseReader(new InputStreamReader(getIdUrlConnection.getInputStream())).getAsJsonObject().get("id").getAsString();
                    return getSkin(StringUtils.from32String(id)).get();
                }
            } catch (IOException | InterruptedException | ExecutionException ignored) {
            }
            return List.of();
        });
    }

}
