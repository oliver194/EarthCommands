package me.oliver193.earthCommands.commands;

import me.oliver193.earthCommands.Msg;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.HashMap;
import java.util.Random;
import java.util.UUID;

public class RTPCommand implements CommandExecutor, Listener {
    private final int MAX_RANGE = 1000; // Max range for random teleport
    private final HashMap<UUID, Long> cooldowns = new HashMap<>(); // Cooldown tracker

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if (!(commandSender instanceof Player)) {
            Msg.send(commandSender, "Only players can use this command!");
            return true;
        }

        Player player = (Player) commandSender;

        // Cooldown check
        if (cooldowns.containsKey(player.getUniqueId())) {
            long timeLeft = (cooldowns.get(player.getUniqueId()) - System.currentTimeMillis()) / 1000;
            if (timeLeft > 0) {
                Msg.send(player, "&cYou must wait " + timeLeft + " seconds before using this command again!");
                return true;
            }
        }

        openConfirmationGUI(player);
        return true;
    }

    private void openConfirmationGUI(Player player) {
        Inventory gui = Bukkit.createInventory(null, 9, "Confirm Teleport");

        // Confirm button (Green)
        ItemStack confirm = new ItemStack(Material.LIME_WOOL);
        ItemMeta confirmMeta = confirm.getItemMeta();
        confirmMeta.setDisplayName("✔ Confirm");
        confirm.setItemMeta(confirmMeta);

        // Cancel button (Red)
        ItemStack cancel = new ItemStack(Material.RED_WOOL);
        ItemMeta cancelMeta = cancel.getItemMeta();
        cancelMeta.setDisplayName("✖ Cancel");
        cancel.setItemMeta(cancelMeta);

        // Add items to GUI
        gui.setItem(3, confirm);
        gui.setItem(5, cancel);

        player.openInventory(gui);
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if (!(event.getWhoClicked() instanceof Player)) return;
        Player player = (Player) event.getWhoClicked();

        if (event.getView().getTitle().equals("Confirm Teleport")) {
            event.setCancelled(true); // Prevent taking items

            ItemStack clickedItem = event.getCurrentItem();
            if (clickedItem == null) return;

            if (clickedItem.getType() == Material.LIME_WOOL) {
                player.closeInventory();
                teleportPlayer(player);
            } else if (clickedItem.getType() == Material.RED_WOOL) {
                player.closeInventory();
                Msg.send(player, "&cTeleportation canceled.");
            }
        }
    }

    private void teleportPlayer(Player player) {
        World world = player.getWorld();
        Location randomLocation = getRandomSafeLocation(world);

        if (randomLocation != null) {
            player.teleport(randomLocation);
            Msg.send(player, "&aTeleported to: &e"
                    + randomLocation.getBlockX() + ", "
                    + randomLocation.getBlockY() + ", "
                    + randomLocation.getBlockZ());

            // Set cooldown for 1 minute (60,000 ms)
            cooldowns.put(player.getUniqueId(), System.currentTimeMillis() + 60000);
        } else {
            Msg.send(player, "&cFailed to find a safe location. Try again!");
        }
    }

    private Location getRandomSafeLocation(World world) {
        Random random = new Random();
        Location location;
        int attempts = 10;

        for (int i = 0; i < attempts; i++) {
            int x = random.nextInt(MAX_RANGE * 2) - MAX_RANGE;
            int z = random.nextInt(MAX_RANGE * 2) - MAX_RANGE;
            int y = world.getHighestBlockYAt(x, z);

            location = new Location(world, x + 0.5, y + 1, z + 0.5);

            if (isSafeLocation(location)) {
                return location;
            }
        }
        return null;
    }

    private boolean isSafeLocation(Location location) {
        Material blockType = location.getBlock().getType();
        Material belowBlockType = location.clone().subtract(0, 1, 0).getBlock().getType();

        return blockType == Material.AIR && belowBlockType.isSolid() && belowBlockType != Material.LAVA;
    }
}
