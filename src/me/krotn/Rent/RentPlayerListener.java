package me.krotn.Rent;

import java.util.logging.Logger;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerListener;

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
