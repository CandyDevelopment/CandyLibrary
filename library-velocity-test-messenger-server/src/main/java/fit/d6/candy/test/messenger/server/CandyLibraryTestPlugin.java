package fit.d6.candy.test.messenger.server;

import com.google.inject.Inject;
import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.proxy.ProxyInitializeEvent;
import com.velocitypowered.api.plugin.Dependency;
import com.velocitypowered.api.plugin.Plugin;
import com.velocitypowered.api.proxy.ProxyServer;
import fit.d6.candy.api.messenger.MessengerProtocol;
import fit.d6.candy.api.messenger.MessengerService;
import fit.d6.candy.api.messenger.server.ServerOptions;
import org.slf4j.Logger;

@Plugin(
        id = "candylibrarymessengerservertest",
        name = "Candy Library Messenger Server Test",
        version = "1.0.0",
        url = "https://github.com/CandyDevelopment/CandyLibrary",
        description = "Candy Library messenger server test",
        authors = {"DeeChael"},
        dependencies = {
                @Dependency(id = "candylibrary")
        }
)
public class CandyLibraryTestPlugin {

    private final ProxyServer server;
    private final Logger logger;

    @Inject
    public CandyLibraryTestPlugin(ProxyServer server, Logger logger) {
        this.server = server;
        this.logger = logger;
    }

    @Subscribe
    public void onProxyInitialization(ProxyInitializeEvent event) {
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
                .protocol(MessengerProtocol.WEBSOCKET));
    }

}
