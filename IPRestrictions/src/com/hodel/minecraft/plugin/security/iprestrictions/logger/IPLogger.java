package com.hodel.minecraft.plugin.security.iprestrictions.logger;

import com.hodel.minecraft.plugin.security.iprestrictions.IPRestrictions;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * This is the AntiMulti logger. This should be used since this is able to write
 * to a file and to the console.
 *
 * @version 1.0
 * @author Joshua
 * @since 1.2
 */
public class IPLogger {

    private static Logger logger;

    /**
     * Creates the writer and logger instance for the rest of the class. MUST BE
     * USED BEFORE WRITING
     *
     * @param plugin The plugin
     */
    public static void setup(IPRestrictions plugin) {
        logger = plugin.getLogger();
    }

    /**
     * Writes a message to the console and file with the "INFO" priority
     *
     * @param message The message to write
     */
    public static void info(String message) {
        log(Level.INFO, message);
    }

    /**
     * Writes a message to the console and file with the "WARNING" priority
     *
     * @param message The message to write
     */
    public static void warning(String message) {
        log(Level.WARNING, message);
    }

    /**
     * Writes a message to the console and file with the "SEVERE" priority
     *
     * @param message The message to write
     */
    public static void severe(String message) {
        log(Level.SEVERE, message);
    }

    /**
     * Writes a message to the console and file with any priority passed
     *
     * @param level The Level to log as
     * @param message The message to write
     */
    public static void log(Level level, String message) {
        logger.log(level, message);
    }

    /**
     * Logs an error to the console and to the file.
     *
     * @param error The error thrown
     * @param message Message to add to error if desired. Can be null.
     */
    public static void error(Throwable error, String message) {
        logger.log(Level.SEVERE, message, error);
    }

    /**
     * Logs an error to the file and console. Can be used instead of
     * AMLogger.error(error, null)
     *
     * @param error The error thrown
     */
    public static void error(Throwable error) {
        error(error, null);
    }

    private IPLogger() {
    }
}
