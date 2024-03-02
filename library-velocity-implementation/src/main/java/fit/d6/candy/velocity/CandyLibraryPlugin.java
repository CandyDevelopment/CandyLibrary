package fit.d6.candy.velocity;


import com.google.inject.Inject;
import com.velocitypowered.api.plugin.Plugin;
import com.velocitypowered.api.proxy.ProxyServer;
import fit.d6.candy.api.CandyLibrary;
import fit.d6.candy.api.CandyVersion;
import fit.d6.candy.api.Service;
import fit.d6.candy.api.messenger.MessengerService;
import fit.d6.candy.velocity.messenger.VelocityMessengerService;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;

import java.util.HashMap;
import java.util.Map;

@Plugin(
        id = "candylibrary",
        name = "Candy Library",
        version = "1.0.0",
        url = "https://github.com/CandyDevelopment/CandyLibrary",
        description = "A powerful library",
        authors = {"DeeChael"}
)
public class CandyLibraryPlugin implements CandyLibrary {

    private static CandyLibraryPlugin instance;

    private final Map<Class<?>, Service> services = new HashMap<>();

    private final ProxyServer server;
    private final Logger logger;

    @Inject
    public CandyLibraryPlugin(ProxyServer server, Logger logger) {
        instance = this;

        this.server = server;
        this.logger = logger;

        this.services.put(MessengerService.class, new VelocityMessengerService());
    }

    @Override
    @SuppressWarnings("unchecked")
    public <S extends Service> @NotNull S getService(@NotNull Class<S> clazz) {
        return (S) this.services.get(clazz);
    }

    @Override
    public <S extends Service> boolean isServiceConfigured(@NotNull Class<S> clazz) {
        return this.services.containsKey(clazz);
    }

    @Override
    public @NotNull CandyVersion getVersion() {
        return CandyVersion.PROXY;
    }

    public static CandyLibraryPlugin getInstance() {
        return instance;
    }

}
