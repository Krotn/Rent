package me.krotn.Rent;

import java.util.logging.Logger;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerListener;

/**
 * This class listens for player events from Bukkit.
 * 
 * Currently it receives only PlayerJoin events.
 * 
 * @see org.bukkit.event.player
 * @see org.bukkit.event.player.PlayerJoinEvent
 */

public class RentPlayerListener extends PlayerListener{
	
	private final Rent plugin;
	Logger log = Logger.getLogger("Minecraft");
	
	public RentPlayerListener(Rent plugin){
		this.plugin = plugin;
	}
	
	@Override
	public void onPlayerJoin(PlayerJoinEvent event){
		
	}
	
}
