package fit.d6.candy.api.protocol;

import org.jetbrains.annotations.NotNull;

public interface PacketManager {

    @NotNull
    ClientboundManager clientbound();

    @NotNull
    ServerboundManager serverbound();

}
