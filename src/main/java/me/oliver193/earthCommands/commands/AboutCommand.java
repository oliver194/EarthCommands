package me.oliver193.earthCommands.commands;

import me.oliver193.earthCommands.Msg;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class AboutCommand implements CommandExecutor {

    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        Msg.send(commandSender, "&7------------= &aAbout Server &7=------------");
        Msg.send(commandSender, "&fStart date: &a31/01/2025");
        Msg.send(commandSender, "&fHosted by: &aoliver193");
        Msg.send(commandSender, "&fCountry: &aUnited Kingdom");
        Msg.send(commandSender, "&fWorld: &ahttps&f://earth.motfe.net");

        return true;
    }
}
