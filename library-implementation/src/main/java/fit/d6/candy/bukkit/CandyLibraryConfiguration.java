package fit.d6.candy.bukkit;

import fit.d6.candy.api.configuration.annotation.Configuration;
import fit.d6.candy.api.configuration.annotation.Node;

@Configuration
public class CandyLibraryConfiguration {

    @Node("gui")
    public boolean gui = true;
    @Node("command")
    public boolean command = true;
    @Node("configuration")
    public boolean configuration = true;
    @Node("nbt")
    public boolean nbt = true;
    @Node("visual")
    public boolean visual = true;
    @Node("player")
    public boolean player = true;
    @Node("protocol")
    public boolean protocol = true;
    @Node("collection")
    public boolean collection = true;
    @Node("database")
    public boolean database = true;
    @Node("item")
    public boolean item = true;
    @Node("time")
    public boolean time = true;
    @Node("scheduler")
    public boolean scheduler = true;
    @Node("world")
    public boolean world = true;
    @Node("event")
    public boolean event = true;
    @Node("messenger")
    public boolean messenger = true;

}
