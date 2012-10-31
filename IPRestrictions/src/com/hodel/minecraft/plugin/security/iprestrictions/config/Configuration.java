package com.hodel.minecraft.plugin.security.iprestrictions.config;

import com.hodel.minecraft.plugin.security.iprestrictions.IPRestrictions;
import com.hodel.minecraft.plugin.security.iprestrictions.logger.IPLogger;
import com.hodel.minecraft.plugin.security.iprestrictions.utils.Formatter;
import java.io.File;
import java.io.IOException;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;

/**
 * @version 1.0
 * @author Joshua
 * @since 1.2
 */
public class Configuration {

    private static IPRestrictions plugin;
    private static int playerIpsAllowed = 2;
    private static int playerTimeLimit = 240;
    private static String msgWhitelist = "You are not whitelisted";
    private static String msgLimit = "Too many IPs used";

    private static boolean useIPLimit = true;
    private static boolean useTimeLimit = true;
    private static boolean useWhitelist = true;

    private static String sqlHost = "localhost";
    private static String sqlPort = "3306";
    private static String sqlUser = "root";
    private static String sqlPass = "password";
    private static String sqlDB = "database";
    private static boolean sqlEnable = false;

    /**
     * Loads the config for the instance of the IPRestrictions plugin passed. This
     * also checks to see if the current config is up to date and loads the
     * values to the RAM for fast access later.
     *
     * @param aP The IPRestrictions instance to load the config for
     */
    public static void loadConfig(IPRestrictions aP) {
        plugin = aP;
        FileConfiguration config = plugin.getConfig();
        if (!(new File(plugin.getDataFolder(), "config.yml").exists())) {
            IPLogger.info("No config found, generating default config");
            plugin.saveDefaultConfig();
        }
        if (config.getString("version") == null || !config.getString("version", plugin.getDescription().getVersion()).equalsIgnoreCase(plugin.getDescription().getVersion())) {
            IPLogger.info("Older config version detected, updating config to new version");
            convert(config);
        }

        playerIpsAllowed = config.getInt("limits.player.ips-allowed", playerIpsAllowed);
        playerTimeLimit  = config.getInt("limits.player.time-limit", playerTimeLimit);

        msgWhitelist = config.getString("messages.whitelist", msgWhitelist);
        msgLimit     = config.getString("messages.limit", msgLimit);

        useIPLimit   = config.getBoolean("protection.ip-limit", useIPLimit);
        useTimeLimit = config.getBoolean("protection.time-limit", useTimeLimit);
        useWhitelist = config.getBoolean("protection.whitelist", useWhitelist);
        
        sqlEnable = config.getBoolean("mysql.enable", sqlEnable);
        sqlHost = config.getString("mysql.host", sqlHost);
        sqlPort = config.getString("mysql.port", sqlPort);
        sqlUser = config.getString("mysql.user", sqlUser);
        sqlPass = config.getString("mysql.pass", sqlPass);
        sqlDB = config.getString("mysql.db", sqlDB);
        
        config.set("limits.player.ips-allowed", playerIpsAllowed);
        config.set("limits.player.time-limit", playerTimeLimit);

        config.set("messages.whitelist", msgWhitelist);
        config.set("messages.limit", msgLimit);
        
        config.set("protection.ip-limit", useIPLimit);
        config.set("protection.time-limit", useTimeLimit);
        config.set("protection.whitelist", useWhitelist);
        
        config.set("mysql.enable", sqlEnable);
        config.set("mysql.host", sqlHost);
        config.set("mysql.port", sqlPort);
        config.set("mysql.user", sqlUser);
        config.set("mysql.pass", sqlPass);
        config.set("mysql.db", sqlDB);
        config.set("version", plugin.getDescription().getVersion());
        try {
            config.save(new File(plugin.getDataFolder(), "config.yml"));
        } catch (IOException ex) {
            IPLogger.error(ex, "Error saving IPRestrictions config");
        }

        if (!useIPLimit()) {
        	IPLogger.info("IP limit disabled in configuration file.");
        }
        
        if (!Bukkit.getOnlineMode()) {
            useIPLimit = true;
        }
        msgWhitelist = Formatter.handleColorCodes(msgWhitelist);
        msgLimit = Formatter.handleColorCodes(msgLimit);
    }

    private static void convert(FileConfiguration config) {
        IPLogger.info("Going to try to update your config from " + config.getString("version", "an unknown version (will assume 0.1)") + " to " + plugin.getDescription().getVersion());
        String oldversion = config.getString("version", "0.1").toLowerCase().trim();
        if (oldversion.equalsIgnoreCase("2.0.2")) {
//            loginThroddle = config.getInt("logins-per-second", loginThroddle);
        }

//        config.set("limits.member.IP", playerIPLimit);
        config.set("version", plugin.getDescription().getVersion());
        try {
            config.save(new File(plugin.getDataFolder(), "config.yml"));
        } catch (IOException ex) {
            IPLogger.error(ex, "Error saving IPRestrictions config");
        }
    }

    public static String getPluginVersion() {
        return plugin.getDescription().getVersion();
    }

    public static int getPlayerIpsAllowed() {
        return playerIpsAllowed;
    }
    public static int getPlayerTimeLimit() {
        return playerTimeLimit;
    }
    
    public static boolean useIPLimit() {
        return useIPLimit;
    }

    public static boolean useTimeLimit() {
        return useTimeLimit;
    }

    public static boolean useWhitelist() {
        return useWhitelist;
    }
    
    public static String getMsgWhitelist() {
    	return msgWhitelist;
    }
    
    public static String getMsgLimit() {
    	if (useTimeLimit()) {
    		return msgLimit + " Current limit " + getPlayerTimeLimit() + " seconds.";
    	}
    	return msgLimit;
    }
    
    public static IPRestrictions getPlugin() {
        return plugin;
    }

    public static void reloadConfig() {
        loadConfig(plugin);
    }

    public static String[] getSQLInfo() {
        return new String[]{
                    sqlHost,
                    sqlPort,
                    sqlUser,
                    sqlPass,
                    sqlDB
                };
    }

    public static boolean useSQL() {
        return sqlEnable;
    }

    private Configuration() {
    }
}