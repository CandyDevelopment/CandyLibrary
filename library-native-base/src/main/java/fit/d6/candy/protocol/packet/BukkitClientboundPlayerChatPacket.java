package fit.d6.candy.protocol.packet;

import fit.d6.candy.api.protocol.packet.ClientboundPlayerChatPacket;
import fit.d6.candy.api.protocol.packet.PacketType;
import net.kyori.adventure.text.Component;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

public class BukkitClientboundPlayerChatPacket extends BukkitPacket implements ClientboundPlayerChatPacket {

    private final UUID sender;
    private final int index;
    private Component content;

    public BukkitClientboundPlayerChatPacket(Object original, UUID sender, int index, Component content) {
        super(original, PacketType.CLIENTBOUND_PLAYER_CHAT);
        this.sender = sender;
        this.index = index;
        this.content = content;
    }

    @Override
    public @NotNull UUID getSender() {
        return this.sender;
    }

    @Override
    public int getIndex() {
        return this.index;
    }

    @Override
    public @NotNull Component getContent() {
        return this.content;
    }

    @Override
    public void setContent(@NotNull Component content) {
        this.content = content;
        this.modified = true;
    }

}
