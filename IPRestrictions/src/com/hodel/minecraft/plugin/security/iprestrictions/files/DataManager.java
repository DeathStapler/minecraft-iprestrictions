package com.hodel.minecraft.plugin.security.iprestrictions.files;

import com.hodel.minecraft.plugin.security.iprestrictions.config.Configuration;
import org.bukkit.entity.Player;

/**
 * @version 1.0
 * @author Joshua
 * @since 1.2
 */
public final class DataManager {

    private Manager manager;

    public DataManager() {
        if (Configuration.useSQL()) {
            manager = new SQLManager();
            manager = manager.setup();
        } else {
//ERROR  MySQL supported only
        }
        
    }

    public String[] getIPs(String name, int playerTimeLimit) {
        if (name == null) {
            return null;
        }
        return manager.getIPs(name.toLowerCase().trim(), playerTimeLimit);
    }

    public String[] getIPs(String name) {
        if (name == null) {
            return null;
        }
        return manager.getIPs(name.toLowerCase().trim());
    }
    
    public String[] getIPs(Player player) {
        if (player == null) {
            return null;
        }
        return getIPs(player.getName());
    }

    public void addIP(String name, String ip) {
        if (name == null || ip == null) {
            return;
        }
        manager.addIP(name.toLowerCase().trim(), ip.toLowerCase().trim());
    }

    public void addIP(Player player) {
        if (player == null) {
            return;
        }
        addIP(player.getName(), player.getAddress().getAddress().getHostAddress());
    }

    public void reload() {
        manager.close();
        if (Configuration.useSQL()) {
            manager = new SQLManager();
        } else {
// ERROR MySQL supported only
        }
        manager = manager.setup();
    }
}