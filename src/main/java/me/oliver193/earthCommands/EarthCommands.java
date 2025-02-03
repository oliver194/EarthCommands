package me.oliver193.earthCommands;

import me.oliver193.earthCommands.commands.*;
import org.bukkit.plugin.java.JavaPlugin;

public final class EarthCommands extends JavaPlugin {

    public void onEnable() {


        // Plugin startup logic
        getLogger().info("Enabling EarthCommands.");

        getCommand("serverinfo").setExecutor(new ServerInfoCommand());
        getCommand("map").setExecutor(new MapCommand());
        getCommand("about").setExecutor(new AboutCommand());

    }

    public void onDisable() {
        // Plugin shutdown logic
        getLogger().info("Disabling EarthCommands.");

    }
}
