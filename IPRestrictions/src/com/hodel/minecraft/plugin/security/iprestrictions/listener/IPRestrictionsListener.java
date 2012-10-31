package com.hodel.minecraft.plugin.security.iprestrictions.listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerLoginEvent.Result;

public class IPRestrictionsListener implements Listener {

    @EventHandler(priority = EventPriority.HIGH)
    public void onPlayerLogin(PlayerLoginEvent event) {
        /*
         * this method is called every time a player logs in you can find out more about the event 
         * via the given parameter
         * e.g. we can determine which player logged in and send him a welcome message
         */
    	event.disallow(Result.KICK_OTHER, "You are kicked");
//        event.getPlayer().sendMessage("******-----Welcome to the sever!");
    }
    
    
    
    
/*    
    @EventHandler // This tells bukkit that you are handling this event.
    public void onBlockPlace(BlockPlaceEvent event) { // We are handling a BlockPlaceEvent, which happens when a block is placed. (:O)
        Player player = event.getPlayer(); // Find out the player that caused the event, save it into "player"
        Block block = event.getBlockPlaced(); // Find out the block that was placed, save it into "block"

        if (block.getType() == Material.SAND ) { // && !player.isOp()) { // If the block is TNT and the player is not an Op ( ! means not)
            player.sendMessage(ChatColor.RED + "You can't place Sand.  That's crazy!"); // Sends the player a message, Feel free to change :D
            event.setCancelled(true); // Remove the block and puts back what was ever there before
        }
    }
    */
}
