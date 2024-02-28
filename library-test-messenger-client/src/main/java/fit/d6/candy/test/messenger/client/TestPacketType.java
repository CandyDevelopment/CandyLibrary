package fit.d6.candy.test.messenger.client;

import fit.d6.candy.api.messenger.packet.PacketType;
import org.jetbrains.annotations.NotNull;

public class TestPacketType implements PacketType<TestPacket> {

    public final static TestPacketType INSTANCE = new TestPacketType();

    private TestPacketType() {
    }

    @Override
    public @NotNull String getId() {
        return "test_packet";
    }

}
