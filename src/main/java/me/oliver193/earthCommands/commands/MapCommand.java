package me.oliver193.earthCommands.commands;

import me.oliver193.earthCommands.Msg;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

public class MapCommand implements CommandExecutor {

    private JavaPlugin plugin;

    public MapCommand(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        // Ensure the file exists before attempting to load
        File file = new File(plugin.getDataFolder(), "config.yml");

        if (!file.exists()) {
            // If the file doesn't exist, inform the player and log the issue
            Msg.send(commandSender, "&cError: config.yml is missing!");
            plugin.getLogger().severe("config.yml is missing!");
            return true;
        }

        // Load the config.yml file
        FileConfiguration config = YamlConfiguration.loadConfiguration(file);

        // Get values from the config.yml file
        String mapURL = config.getString("server_info.map_url", "Change map_url in config.yml to your URL.");

        Msg.send(commandSender, "&7------------= &aServer Map &7=------------");
        Msg.send(commandSender, "&fLink: &a" + mapURL);

        return true;
    }
}
