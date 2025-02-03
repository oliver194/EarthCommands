package me.oliver193.earthCommands.commands;

import me.oliver193.earthCommands.Msg;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

public class AboutCommand implements CommandExecutor {

    private JavaPlugin plugin;

    public AboutCommand(JavaPlugin plugin) {
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
        String startDate = config.getString("server_info.start_date", "Change start_date in config.yml to the start date of your server.");
        String hostedBy = config.getString("server_info.hosted_by", "Change hosted_by in config.yml to your name.");
        String country = config.getString("server_info.country", "Change country in config.yml to the country your server is in.");

        // Send the information to the player
        Msg.send(commandSender, "&7------------= &aAbout Server &7=------------");
        Msg.send(commandSender, "&fStart date: &a" + startDate);
        Msg.send(commandSender, "&fHosted by: &a" + hostedBy);
        Msg.send(commandSender, "&fCountry: &a" + country);

        return true;
    }
}
