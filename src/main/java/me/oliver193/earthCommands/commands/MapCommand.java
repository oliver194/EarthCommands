package me.oliver193.earthCommands.commands;

import me.oliver193.earthCommands.Msg;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class MapCommand implements CommandExecutor {

    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        Msg.send(commandSender, "&7------------= &aServer Map &7=------------");
        Msg.send(commandSender, "&fLink: &ahttps&f://earth.0000024.xyz");

        return true;
    }
}
