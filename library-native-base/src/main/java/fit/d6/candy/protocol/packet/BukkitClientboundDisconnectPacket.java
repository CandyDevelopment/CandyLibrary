package fit.d6.candy.protocol.packet;

import fit.d6.candy.api.protocol.packet.ClientboundDisconnectPacket;
import fit.d6.candy.api.protocol.packet.PacketType;
import net.kyori.adventure.text.Component;
import org.jetbrains.annotations.NotNull;

public class BukkitClientboundDisconnectPacket extends BukkitPacket implements ClientboundDisconnectPacket {

    private Component reason;

    public BukkitClientboundDisconnectPacket(Object original, Component reason) {
        super(original, PacketType.CLIENTBOUND_DISCONNECT);
        this.reason = reason;
    }

    @Override
    public @NotNull Component getReason() {
        return this.reason;
    }

    @Override
    public void setReason(@NotNull Component component) {
        this.reason = component;
        this.modified = true;
    }

}
