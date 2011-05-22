package me.krotn.Rent;

import java.util.logging.Logger;
import org.bukkit.event.Event;
import org.bukkit.event.Event.Priority;
import org.bukkit.event.player.PlayerListener;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class Rent extends JavaPlugin{
	
	Logger log = Logger.getLogger("Minecraft");
	private final PlayerListener playerListener = new RentPlayerListener(this);
	
	public void onEnable(){
		log.info("RENT: Rent enabled");
		PluginManager pluginManager = getServer().getPluginManager();
		pluginManager.registerEvent(Event.Type.PLAYER_JOIN, playerListener, Priority.Normal, this);
	}
	
	public void onDisable(){
		log.info("RENT: Rent disabled");
	}
}
