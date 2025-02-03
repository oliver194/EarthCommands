package me.oliver193.earthCommands.commands;

import me.oliver193.earthCommands.Msg;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import oshi.SystemInfo;
import oshi.hardware.CentralProcessor;
import oshi.hardware.HardwareAbstractionLayer;

import java.lang.management.ManagementFactory;

public class ServerInfoCommand implements CommandExecutor {

    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        System.gc();
        Runtime rt = Runtime.getRuntime();
        long usedMB = (rt.totalMemory() - rt.freeMemory()) / 1024 / 1024;
        long maxMB = (rt.maxMemory()) / 1024 / 1024;

        // Get processor information
        SystemInfo systemInfo = new SystemInfo();
        HardwareAbstractionLayer hardware = systemInfo.getHardware();
        CentralProcessor processor = hardware.getProcessor();
        CentralProcessor.ProcessorIdentifier processorIdentifier = processor.getProcessorIdentifier();


        // Get uptime in milliseconds
        long milliseconds = ManagementFactory.getRuntimeMXBean().getUptime();
        // formula for conversion for
        // milliseconds to minutes.
        long minutes = (milliseconds / 1000) / 60;

        // formula for conversion for
        // milliseconds to seconds
        long seconds = (milliseconds / 1000) % 60;


        Msg.send(commandSender, "&7------------= &aServer Info &7=------------");
        Msg.send(commandSender, "Version: " + Bukkit.getBukkitVersion());
        Msg.send(commandSender, "Software: " + Bukkit.getName());
        Msg.send(commandSender, "Java Version: " + System.getProperty("java.version"));
        Msg.send(commandSender, "Uptime: " + minutes + " minutes and " + seconds + " seconds");
        Msg.send(commandSender, "OS: " + System.getProperty("os.name") + " (" + System.getProperty("os.version") + ") " + System.getProperty("os.arch"));
        Msg.send(commandSender, "CPU: " + processorIdentifier.getName());
        Msg.send(commandSender, "CPU Speed: " + processorIdentifier.getVendorFreq() / 1000000000.0 + " GHz");
        Msg.send(commandSender, "Core Count: " + processor.getPhysicalProcessorCount() + " cores / " + processor.getLogicalProcessorCount() + " threads");
        Msg.send(commandSender, "RAM Usage: " + usedMB + " MB" + " of " + maxMB + " MB");

        return true;
    }
}
