package me.oliver193.earthCommands.utils;

import me.oliver193.earthCommands.EarthCommands;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class TaxManager {
    private static final double TAX_PERCENTAGE = 0.20; // 20% tax
    private static final long TAX_INTERVAL = 7 * 24 * 60 * 60 * 20L; // 7 days in ticks
    private static final long WARNING_TIME = TAX_INTERVAL - 1200; // 1 minute before taxes

    public static void startTaxScheduler(EarthCommands plugin) {
        new BukkitRunnable() {
            @Override
            public void run() {
                collectTaxes();
            }
        }.runTaskTimer(plugin, 0L, TAX_INTERVAL); // Every 7 days

        // Send a warning 1 minute before tax collection
        new BukkitRunnable() {
            @Override
            public void run() {
                Bukkit.broadcastMessage(ChatColor.YELLOW + "⚠ Taxes will be collected in 1 minute! Make sure you have enough money.");
            }
        }.runTaskTimer(plugin, WARNING_TIME, TAX_INTERVAL);
    }

    public static void collectTaxes() {
        Economy economy = EarthCommands.getEconomy();

        Bukkit.broadcastMessage(ChatColor.YELLOW + "💰 Taxes are being collected now!");

        for (Player player : Bukkit.getOnlinePlayers()) {
            double balance = economy.getBalance(player);
            double taxAmount = balance * TAX_PERCENTAGE;

            if (balance >= taxAmount) {
                economy.withdrawPlayer(player, taxAmount);
                player.sendMessage(ChatColor.RED + "💰 Weekly Tax Deducted: " + ChatColor.GOLD + "$" + taxAmount);
            } else {
                player.sendMessage(ChatColor.RED + "⚠ You don't have enough money for taxes!");
            }
        }
    }
}