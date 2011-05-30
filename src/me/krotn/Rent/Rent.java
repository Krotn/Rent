package me.krotn.Rent;

import java.util.logging.Logger;
import org.bukkit.event.Event;
import org.bukkit.event.Event.Priority;
import org.bukkit.event.player.PlayerListener;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class Rent extends JavaPlugin{
	
	private Logger log = Logger.getLogger("Minecraft");
	private RentLogManager logManager = new RentLogManager(log);
	private RentDatabaseManager dbman = new RentDatabaseManager();
	private RentPropertiesManager propman = new RentPropertiesManager();
	private final PlayerListener playerListener = new RentPlayerListener(this);
	private RentDateUtils dateUtils = new RentDateUtils(this,dbman);
	private RentCalculationsManager calcMan = new RentCalculationsManager(this);
	private RentPermissionsManager permMan = new RentPermissionsManager(this);
	
	public void onEnable(){
		logManager.info("Rent enabled");
		PluginManager pluginManager = getServer().getPluginManager();
		pluginManager.registerEvent(Event.Type.PLAYER_JOIN, playerListener, Priority.Normal, this);
		dbman.connect();
		if(!propman.fileExists()){
			propman.setup();
		}
	}
	
	public void onDisable(){
		logManager.info("Rent disabled");
		dbman.disconnect();
	}
	
	public RentLogManager getLogManager(){
		return logManager;
	}
	
	public RentDatabaseManager getDatabaseManager(){
		return this.dbman;
	}
	
	public RentPropertiesManager getPropertiesManager(){
		return this.propman;
	}
	
	public RentDateUtils getDateUtils(){
		return dateUtils;
	}
	
	public RentCalculationsManager getCalculationsManager(){
		return calcMan;
	}
	
	public RentPermissionsManager getPermissionsManager(){
		return permMan;
	}
}
