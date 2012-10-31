package com.hodel.minecraft.plugin.security.iprestrictions.listener;

import com.hodel.minecraft.plugin.security.iprestrictions.IPRestrictions;
import com.hodel.minecraft.plugin.security.iprestrictions.config.Configuration;
import com.hodel.minecraft.plugin.security.iprestrictions.logger.IPLogger;
import java.util.Arrays;
import java.util.List;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerLoginEvent.Result;

public class PlayerListener implements Listener {
	   private IPRestrictions plugin;
	    private int maxIPs = 2;
	    private boolean whitelist;
	    private int playerTimeLimit = 3600; // 1 hr

	    /**
	     * Creates the listener and sets up the local variables for use.
	     *
	     * @param aPlugin The IPRestrictions instance
	     */
	    public PlayerListener(IPRestrictions aPlugin) {
	        plugin = aPlugin;

	        maxIPs = Configuration.getPlayerIpsAllowed();
	        playerTimeLimit = Configuration.getPlayerTimeLimit();
	        whitelist = Configuration.useWhitelist();
	    }

	    /**
	     * Handles the player login event for the plugin. This is where the tests
	     * are ran to determine if the player can connect or not.
	     *
	     * @param event The PlayerLoginEvent to associate with
	     */
	    @EventHandler(priority = EventPriority.LOWEST)
	    public void onPlayerJoin(PlayerLoginEvent event) {
	        if (event.getResult() != Result.ALLOWED) {
	            return;
	        }

	        if (isPlayerOnline(event)) {
	            return;
	        }

	        if (!ip(event)) {
	            return;
	        }
	    }

	    private boolean isPlayerOnline(PlayerLoginEvent event) {
	        String name = event.getPlayer().getName();
	        for (Player player : Bukkit.getOnlinePlayers()) {
	            if (player.getName().equalsIgnoreCase(name)) {
	                return true;
	            }
	        }
	        return false;
	    }

	    private boolean ip(PlayerLoginEvent event) {
	        if (!checkNameToIp(event)) {
	            return false;
	        }

	        return true;
	    }

	    /**
	     * Toggles the AntiMulti whitelist state.
	     *
	     * @param newState The new whitelist state
	     */
	    public void toggleWhitelist(boolean newState) {
	        whitelist = newState;
	    }

	    /**
	     * Returns the status of the whitelist.
	     *
	     * @return True if the whitelist is on, otherwise false
	     */
	    public boolean getWhitelistStatus() {
	        return whitelist;
	    }

	    /**
	     *
	     * @param player The player to check
	     * @param permission The permission to check
	     * @return True if the player has permission, false otherwise
	     */
	    public boolean checkPerm(Player player, String permission) {
	        return player.hasPermission(permission);
	    }


	    private boolean checkNameToIp(PlayerLoginEvent event) {
	    	if (!Configuration.useIPLimit()) {
	    		return true;	    		
	    	}
	        String name = event.getPlayer().getName().toLowerCase();
	        String ip = event.getAddress().getHostAddress();
	        List<String> ips;
	        
	        if (Configuration.useIPLimit()) {
	        	ips = Arrays.asList(plugin.getManager().getIPs(name, playerTimeLimit));
	        } else {
	        	ips = null;
	        }
	        
	        IPLogger.info("[IPR] : Number of IPs " + ips.size());
	        
	        // Record the IP
        	plugin.getManager().addIP(name, ip);

        	if (!Configuration.useIPLimit()) {
        	} else if (Configuration.useIPLimit() && ips.size() <= maxIPs) {
IPLogger.info("IP limit either disabled or not reached for " + name + ".  Current=" + ips.size() + ", Max=" + maxIPs);
	        } else if (Configuration.useIPLimit() && Configuration.useWhitelist() && ips.size() > maxIPs) {
IPLogger.info("IP limit reached for " + name + ".  Checking Whitelist.");
	        	// Add whitelist check
	        	
	        	// Add permission to ignore certain groups
	        	
	        	event.disallow(Result.KICK_OTHER, Configuration.getMsgLimit());
	            return false;
	        } else {
IPLogger.info("IP limit reached for " + name);
	            event.disallow(Result.KICK_OTHER, Configuration.getMsgLimit());
	            return false;
	        }
	        return true;
	    }


}
