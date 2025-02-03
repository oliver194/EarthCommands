package me.oliver193.earthCommands.commands;

import me.oliver193.earthCommands.Msg;
import me.oliver193.earthCommands.utils.TaxManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class TaxCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {

        // Trigger the tax collection
        TaxManager.collectTaxes();  // Assuming this method exists to collect taxes

        Msg.send(commandSender, "&aTax collection has been triggered!");

        return true;
    }
}
