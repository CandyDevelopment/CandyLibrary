package fit.d6.candy.api.position;

import org.bukkit.Location;
import org.bukkit.World;
import org.jetbrains.annotations.NotNull;

public interface BlockPosition {

    int getX();

    int getY();

    int getZ();

    @NotNull
    Location toLocation(@NotNull World world);

}
