package fit.d6.candy.api.protocol.packet;

import net.kyori.adventure.text.Component;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

public interface ClientboundPlayerChatPacket extends Packet {

    @NotNull
    UUID getSender();

    int getIndex();

    @NotNull
    Component getContent();

    void setContent(@NotNull Component content);

}
