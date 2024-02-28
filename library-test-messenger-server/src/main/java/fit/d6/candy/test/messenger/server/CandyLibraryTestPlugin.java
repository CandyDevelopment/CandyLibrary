package fit.d6.candy.test.messenger.server;

import fit.d6.candy.api.messenger.MessengerProtocol;
import fit.d6.candy.api.messenger.MessengerService;
import fit.d6.candy.api.messenger.server.ServerOptions;
import org.bukkit.plugin.java.JavaPlugin;

public class CandyLibraryTestPlugin extends JavaPlugin {

    @Override
    public void onEnable() {
        MessengerService.getService().getPacketManager().register(TestPacketType.INSTANCE, TestPacket::new);
        MessengerService.getService().getMessengerManager().server(19856, ServerOptions.of()
                .connector((server, connection) -> {
                    System.out.println("Connected");
                })
                .receiver((server, connection, packet) -> {
                    System.out.println(packet);
                    if (packet instanceof TestPacket testPacket) {
                        System.out.println("Message: " + testPacket.getMessage());
                        connection.send(new TestPacket("Received, great!"));
                    }
                })
                .closer((server, connection) -> {
                    System.out.println("Closed");
                })
                .protocol(MessengerProtocol.TCP));
    }

}
