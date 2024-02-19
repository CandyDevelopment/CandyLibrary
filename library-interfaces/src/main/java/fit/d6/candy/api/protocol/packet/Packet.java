package fit.d6.candy.api.protocol.packet;

public interface Packet {

    PacketType getType();

    boolean isCancelled();

    void setCancelled(boolean isCancelled);

}
