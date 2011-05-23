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
	private RentDatabaseManager dbMan;
	
	public RentPlayerListener(Rent plugin){
		this.plugin = plugin;
		this.logManager = this.plugin.getLogManager();
		this.dbMan = this.plugin.getDatabaseManager();
	}
	
	@Override
	public void onPlayerJoin(PlayerJoinEvent event){
		logManager.info("Logged entry for: "+event.getPlayer().getName()+".");
		String playerName = event.getPlayer().getName();
		if(!dbMan.playerExists(playerName)){
			dbMan.addPlayer(playerName);
			logManager.info("New player, "+playerName+" added to the database.");
		}
	}
	
}
