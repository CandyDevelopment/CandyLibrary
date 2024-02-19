package fit.d6.candy.api.protocol.packet;

public enum PacketType {

    CLIENTBOUND_PLAYER_CHAT(true),
    CLIENTBOUND_DISCONNECT(true);

    private final boolean listenable;

    PacketType(boolean listenable) {
        this.listenable = listenable;
    }

    public boolean isListenable() {
        return listenable;
    }

}
