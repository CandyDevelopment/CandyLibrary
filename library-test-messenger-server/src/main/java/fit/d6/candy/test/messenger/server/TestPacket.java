package fit.d6.candy.test.messenger.server;

import fit.d6.candy.api.messenger.packet.Packet;
import fit.d6.candy.api.messenger.packet.PacketType;
import fit.d6.candy.api.messenger.packet.ReadablePacketContent;
import fit.d6.candy.api.messenger.packet.WritablePacketContent;
import org.jetbrains.annotations.NotNull;

import java.nio.charset.StandardCharsets;

public class TestPacket implements Packet {

    private String message;

    public TestPacket(ReadablePacketContent readablePacketContent) {
        int messageLength = readablePacketContent.readInt();
        this.message = readablePacketContent.readString(messageLength, StandardCharsets.UTF_8);
    }

    public TestPacket(String message) {
        this.message = message;
    }

    @Override
    public void serialize(@NotNull WritablePacketContent content) {
        content.writeInt(this.message.length());
        content.writeString(this.message, StandardCharsets.UTF_8);
    }

    @Override
    public @NotNull PacketType<?> getType() {
        return TestPacketType.INSTANCE;
    }

    public String getMessage() {
        return message;
    }
}
