package fit.d6.candy.test.messenger.client;

import fit.d6.candy.api.messenger.Address;
import fit.d6.candy.api.messenger.MessengerProtocol;
import fit.d6.candy.api.messenger.MessengerService;
import fit.d6.candy.api.messenger.client.ClientOptions;
import org.bukkit.plugin.java.JavaPlugin;

import java.net.URI;
import java.net.URISyntaxException;

public class CandyLibraryTestPlugin extends JavaPlugin {

    @Override
    public void onEnable() {
        MessengerService.getService().getPacketManager().register(TestPacketType.INSTANCE, TestPacket::new);
        try {
            MessengerService.getService().getMessengerManager()
                    .client(1, ClientOptions.of()
                            .connector((self, connection) -> {
                                System.out.println("Connected");
                                connection.send(new TestPacket("I wanna know!"));
                            })
                            .receiver((self, connection, packet) -> {
                                System.out.println(packet);
                                if (packet instanceof TestPacket testPacket) {
                                    System.out.println("Message: " + testPacket.getMessage());
                                }
                            })
                            .closer((self) -> {
                                System.out.println("Closed");
                            })
                            .keepalive(true)
                            .address(Address.websocket(new URI("ws://127.0.0.1:19856")))
                            .protocol(MessengerProtocol.WEBSOCKET));
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

}
