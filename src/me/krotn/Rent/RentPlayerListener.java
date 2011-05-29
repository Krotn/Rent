package me.krotn.Rent;

import java.util.logging.Logger;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
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
	private RentPropertiesManager propMan;
	
	public RentPlayerListener(Rent plugin){
		this.plugin = plugin;
		this.logManager = this.plugin.getLogManager();
		this.dbMan = this.plugin.getDatabaseManager();
		this.propMan = this.plugin.getPropertiesManager();
	}
	
	@Override
	public void onPlayerJoin(PlayerJoinEvent event){
		logManager.info("Logged entry for: "+event.getPlayer().getName()+".");
		String playerName = event.getPlayer().getName();
		Player player = event.getPlayer();
		if(!dbMan.playerExists(playerName)){
			dbMan.addPlayer(playerName);
			logManager.info("New player, "+playerName+" added to the database.");
		}
		plugin.getDateUtils().sanityCheck(); //Make sure that current month exists.
		int monthID = dbMan.getMonthID(plugin.getDateUtils().getCurrentMonth());
		int playerID = dbMan.getPlayerID(event.getPlayer().getName().toLowerCase());
		if(!dbMan.hasPlayerLoggedIn(dbMan.getPlayerID(event.getPlayer().getName().toLowerCase()), monthID)){
			dbMan.addLogin(playerID, monthID);
			logManager.info("First monthly login for: "+event.getPlayer().getName()+".");
			String loginMessage = propMan.getProperty("firstMonthlyLoginMessage");
			player.sendRawMessage(ChatColor.RED+loginMessage);
		}
	}
	
}
