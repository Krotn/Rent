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
	private final RentLogManager logManager;
	
	public RentPlayerListener(Rent plugin){
		this.plugin = plugin;
		this.logManager = this.plugin.getLogManager();
	}
	
	@Override
	public void onPlayerJoin(PlayerJoinEvent event){
		
	}
	
}
