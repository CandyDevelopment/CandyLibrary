package fit.d6.candy.api.protocol.packet;

import net.kyori.adventure.text.Component;
import org.jetbrains.annotations.NotNull;

public interface ClientboundDisconnectPacket extends Packet {

    @NotNull
    Component getReason();

    void setReason(@NotNull Component component);

}
