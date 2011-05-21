package me.krotn.Rent;

import java.util.logging.Logger;
import org.bukkit.plugin.java.JavaPlugin;

public class Rent extends JavaPlugin{
	
	Logger log = Logger.getLogger("Minecraft");
	
	public void onEnable(){
		log.info("Rent enabled");
	}
	
	public void onDisable(){
		log.info("Rent disabled");
	}
}
