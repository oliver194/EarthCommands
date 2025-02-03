package me.oliver193.earthCommands;

import me.oliver193.earthCommands.commands.*;
import me.oliver193.earthCommands.utils.TaxManager;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

public final class EarthCommands extends JavaPlugin {

    private static Economy econ = null;

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

        // Register commands
        getCommand("serverinfo").setExecutor(new ServerInfoCommand());
        getCommand("map").setExecutor(new MapCommand());
        getCommand("about").setExecutor(new AboutCommand());
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
}
