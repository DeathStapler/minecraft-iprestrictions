package com.hodel.minecraft.plugin.security.iprestrictions.files;

import java.sql.SQLException;


/**
 * @version 1.0
 * @since 2.0.5
 */
public interface Manager {

    /**
     * Returns the IPs that have been used by the player.
     *
     * @param name The name of the player
     * @param playerTimeLimit 
     * @return The array of IPs that has been used
     */
    public abstract String[] getIPs(String name, int playerTimeLimit);
    
    /**
     * Returns the IPs that have been used by the player.
     *
     * @param name The name of the player
     * @param playerTimeLimit 
     * @return The array of IPs that has been used
     */
    public abstract String[] getIPs(String name);

    /**
     * Adds an IP to a name.
     *
     * @param name Name of the player
     * @param ip IP to add to the name
     */
    public abstract void addIP(String name, String ip);
    
    /**
     * Gets the number of IPs for a name.
     *
     * @param name Name of the player
     * @param seconds The number of seconds to look back in time
     */
    public abstract int checkIPCount(String name, int seconds)  throws SQLException;
    
    /**
     * Checks if the name and IP has been used within the last given seconds.
     *
     * @param name Name of the player
     * @param ip IP to add to the name
     * @param seconds The number of seconds to look back in time
     */
    public abstract boolean checkIP(String name, String ip, int seconds)  throws SQLException;

    /**
     * Sets up the manager. This should be used to set up the manager, and
     * return itself or another manager if setup fails.
     *
     * @return
     */
    public abstract Manager setup();

    /**
     * Closes the manager.
     */
    public abstract void close();
}
