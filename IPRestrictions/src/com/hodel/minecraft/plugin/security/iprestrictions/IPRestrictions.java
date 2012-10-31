package com.hodel.minecraft.plugin.security.iprestrictions;

import java.io.File;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import com.hodel.minecraft.plugin.security.iprestrictions.command.CommandManager;
import com.hodel.minecraft.plugin.security.iprestrictions.config.Configuration;
import com.hodel.minecraft.plugin.security.iprestrictions.files.DataManager;
import com.hodel.minecraft.plugin.security.iprestrictions.listener.PlayerListener;
import com.hodel.minecraft.plugin.security.iprestrictions.logger.IPLogger;

public class IPRestrictions extends JavaPlugin  {
	
    public static PlayerListener pListener;
    DataManager manager;
    private static IPRestrictions instance;
	
	//static String mainDirectory = "IPRestrictions/";
	//static File fExceptions = new File(mainDirectory + "Exceptions.yml")
	
    @Override
    public void onLoad() {
    	instance = this;
    	IPLogger.setup(instance);
    }
    
    @Override
    public void onEnable(){
        try {
            Configuration.loadConfig(instance);
            IPLogger.info("Config Info: Limit Message = " + Configuration.getMsgLimit());
            manager = new DataManager();
            pListener = new PlayerListener(instance);
            Bukkit.getPluginManager().registerEvents(pListener, instance);
            CommandManager.setup(instance);
            IPLogger.info(instance.getName() + "-" + instance.getVersion() + " successfully enabled");
        } catch (Throwable ex) {
            IPLogger.error(ex, "An error occurred on startup, disabling " + instance.getName());
            Bukkit.getPluginManager().disablePlugin(instance);
        }
        
        // TODO Insert logic to be performed when the plugin is enabled
    	getLogger().info("IPRestrictions Plugin Enabled!");
    	this.saveDefaultConfig();

    }
 
    @Override
    public void onDisable() {
        // TODO Insert logic to be performed when the plugin is disabled
    	getLogger().info("IPRestrictions Plugin Dinabled!");
    }

    public File getUserFolder() {
        return new File(getDataFolder(), "userData");
    }

    public String getVersion() {
        return this.getDescription().getVersion();
    }

    public DataManager getManager() {
        return manager;
    }

    public static IPRestrictions getPlugin() {
        return instance;
    }

}
