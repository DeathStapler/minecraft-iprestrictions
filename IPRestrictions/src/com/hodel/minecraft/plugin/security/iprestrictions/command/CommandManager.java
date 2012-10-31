package com.hodel.minecraft.plugin.security.iprestrictions.command;

import com.hodel.minecraft.plugin.security.iprestrictions.IPRestrictions;
import com.hodel.minecraft.plugin.security.iprestrictions.command.Ipr;
import java.util.ArrayList;
import java.util.List;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

/**
 * @version 1.1
 * @author icelord871
 * @since 1.0
 */
public abstract class CommandManager implements CommandExecutor {

    protected static IPRestrictions plugin;
    protected static List<CommandManager> commands = new ArrayList<CommandManager>();

    @Override
    public abstract boolean onCommand(CommandSender sender, Command cmd, String alias, String[] args);

    public abstract String getName();

    public abstract String getHelp();

    public abstract void reload();

    public abstract void disable();

    /**
     * Checks to see if a CommandSender has a permission. If the sender is a
     * ConsoleCommandSender, then this returns true.
     *
     * @param sender Sender to check permission
     * @param permission Permission to check
     * @return True if player has the permission, false otherwise
     */
    public static boolean checkPerm(CommandSender sender, String permission) {
        if (sender instanceof ConsoleCommandSender) {
            return true;
        }
        return sender.hasPermission(permission);
    }

    /**
     * Checks to see if a player has a permission..
     *
     * @param player Player to check permission
     * @param permission Permission to check
     * @return True if player has the permission, false otherwise
     */
    public static boolean checkPerm(Player player, String permission) {
        return player.hasPermission(permission);
    }

    /**
     * Sets up the CommandManager. This includes creating the CommandManager
     * instances for each command, define those commands with Bukkit, and return
     * the list.
     *
     * @param aPlugin The AntiMulti instance
     * @return List of registered commands
     */
    public static List<CommandManager> setup(IPRestrictions aPlugin) {
        plugin = aPlugin;

        Ipr add = new Ipr();
        plugin.getCommand(add.getName()).setExecutor(add);
        commands.add(add);

        return getCommands();
    }

    /**
     * Returns a list of commands and their {@link CommandManager}s that are
     * registered with this manager.
     *
     * @return List containing all the {@link CommandManager} commands
     * registered
     */
    public static List<CommandManager> getCommands() {
        return commands;
    }

    /**
     * Disables all the commands registered and removes their executors. This
     * should be used only when the server is stopped
     */
    public static void stop() {
        for (CommandManager exec : commands) {
            exec.disable();
            for (String name : exec.getName().split(",")) {
                plugin.getCommand(name).setExecutor(null);
            }
        }
        commands.clear();
    }

    /**
     * Reloads all the registered commands with AntiMulti
     */
    public static void reloadAll() {
        for (CommandManager exec : commands) {
            exec.reload();
        }
    }
}
