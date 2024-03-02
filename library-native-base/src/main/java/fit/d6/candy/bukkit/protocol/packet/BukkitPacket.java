package fit.d6.candy.bukkit.protocol.packet;

import fit.d6.candy.api.protocol.packet.Packet;
import fit.d6.candy.api.protocol.packet.PacketType;

public abstract class BukkitPacket implements Packet {

    private final Object original;
    private final PacketType type;

    private boolean isCancelled = false;

    boolean modified = false;

    public BukkitPacket(Object original, PacketType type) {
        this.original = original;
        this.type = type;
    }

    public Object getOriginal() {
        return original;
    }

    @Override
    public PacketType getType() {
        return this.type;
    }

    @Override
    public boolean isCancelled() {
        return isCancelled;
    }

    @Override
    public void setCancelled(boolean cancelled) {
        isCancelled = cancelled;
    }

    public boolean isModified() {
        return modified;
    }

}
