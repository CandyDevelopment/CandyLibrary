package fit.d6.candy.api.world;

import org.jetbrains.annotations.NotNull;

public interface Environment {

    @NotNull
    static Environment overworld() {
        return WorldManager.getManager().getEnvironmentOverworld();
    }

    @NotNull
    static Environment nether() {
        return WorldManager.getManager().getEnvironmentNether();
    }

    @NotNull
    static Environment theEnd() {
        return WorldManager.getManager().getEnvironmentTheEnd();
    }

}
