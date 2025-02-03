package me.oliver193.earthCommands;

import me.oliver193.earthCommands.commands.*;
import me.oliver193.earthCommands.utils.TaxManager;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.net.URL;
import java.net.HttpURLConnection;

public final class EarthCommands extends JavaPlugin {

    private static Economy econ = null;
    private static final String REMOTE_CONFIG_URL = "https://raw.githubusercontent.com/oliver194/EarthCommands/refs/heads/master/src/main/resources/config.yml";

    @Override
    public void onEnable() {
        // Log to check if Vault is installed
        Plugin vaultPlugin = getServer().getPluginManager().getPlugin("Vault");
        if (vaultPlugin == null) {
            getLogger().severe("Vault not found! Disabling plugin...");
            getServer().getPluginManager().disablePlugin(this);
            return;
        } else {
            getLogger().info("Vault found and enabled!");
        }

        // Setup Vault economy
        if (!setupEconomy()) {
            getLogger().severe("Vault economy provider not found! Disabling plugin...");
            getServer().getPluginManager().disablePlugin(this);
            return;
        }

        // Check if config.yml exists and handle version updates
        File configFile = new File(getDataFolder(), "config.yml");
        if (!configFile.exists()) {
            this.saveResource("config.yml", false); // Only copy if the file doesn't exist
        }

        // Load the local config file to check the version
        FileConfiguration localConfig = YamlConfiguration.loadConfiguration(configFile);
        String localConfigVersion = localConfig.getString("config_version");

        // Fetch the remote config and get its version
        String remoteConfigVersion = fetchRemoteConfigVersion();

        // Compare the versions and update if necessary
        if (remoteConfigVersion == null) {
            getLogger().severe("Failed to fetch remote config. Config update skipped.");
            return;
        }

        if (!localConfigVersion.equals(remoteConfigVersion)) {
            updateConfigFromRemote(localConfig, configFile, remoteConfigVersion);
            getLogger().info("Config updated to version " + remoteConfigVersion);
        }

        // Register commands
        getCommand("serverinfo").setExecutor(new ServerInfoCommand());
        getCommand("map").setExecutor(new MapCommand(this));
        getCommand("about").setExecutor(new AboutCommand(this)); // Pass the main plugin instance
        getCommand("rtp").setExecutor(new RTPCommand());
        getCommand("taxes").setExecutor(new TaxCommand());

        // Register event listeners
        getServer().getPluginManager().registerEvents(new RTPCommand(), this);

        // Start automatic tax collection
        TaxManager.startTaxScheduler(this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    // Vault Economy Setup
    private boolean setupEconomy() {
        RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager().getRegistration(Economy.class);
        if (rsp == null) return false;
        econ = rsp.getProvider();
        return econ != null;
    }

    public static Economy getEconomy() {
        return econ;
    }

    // Fetch the remote config version from the GitHub URL
    private String fetchRemoteConfigVersion() {
        try {
            URL url = new URL(REMOTE_CONFIG_URL);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setConnectTimeout(5000); // 5 seconds timeout
            connection.setReadTimeout(5000);

            // Read the response
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder response = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                response.append(line).append("\n");
            }
            reader.close();

            // Load the remote config to extract version
            FileConfiguration remoteConfig = YamlConfiguration.loadConfiguration(new java.io.StringReader(response.toString()));
            return remoteConfig.getString("config_version");
        } catch (Exception e) {
            getLogger().severe("Error fetching remote config: " + e.getMessage());
            return null;
        }
    }

    // Update the local config from the remote config and set config_version
    private void updateConfigFromRemote(FileConfiguration localConfig, File configFile, String remoteConfigVersion) {
        try {
            // Fetch the remote config content again
            URL url = new URL(REMOTE_CONFIG_URL);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setConnectTimeout(5000);
            connection.setReadTimeout(5000);

            // Read the response from the remote config
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder response = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                response.append(line).append("\n");
            }
            reader.close();

            // Load the remote config content
            FileConfiguration remoteConfig = YamlConfiguration.loadConfiguration(new java.io.StringReader(response.toString()));

            // Overwrite local config with remote config
            localConfig.setDefaults(remoteConfig);

            // Explicitly set the config_version to the new remote version
            localConfig.set("config_version", remoteConfigVersion);

            // Save the updated local config
            localConfig.save(configFile);

        } catch (Exception e) {
            getLogger().severe("Error updating config from remote: " + e.getMessage());
        }
    }
}
