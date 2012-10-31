package com.hodel.minecraft.plugin.security.iprestrictions.command;

import com.hodel.minecraft.plugin.security.iprestrictions.command.CommandManager;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

/**
 * @version 
 * @author 
 * @since 
 */
public class Ipr extends CommandManager {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String alias, String[] args) {
        if (args.length < 1) {
            sender.sendMessage(ChatColor.RED + "You did not put enough arguments");
            sender.sendMessage(ChatColor.RED + getHelp());
            return true;
        }

        // Get the list from the DB
        //
        
        sender.sendMessage(ChatColor.RED + args[1] + " most resent IPs:");
        // Loop thru IPs and display

        
        return true;
    }

    @Override
    public String getName() {
        return "ipr";
    }

    @Override
    public String getHelp() {
        return "ipr <user> - Will list last IPs used to login";
    }

    @Override
    public void reload() {
    }

    @Override
    public void disable() {
    }
}
