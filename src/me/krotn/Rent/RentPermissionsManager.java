package me.krotn.Rent;

import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.permissions.Permissible;

/**
 * This class handles various permissions related tasks for the Rent plugin.
 *
 */
public class RentPermissionsManager {
	private String baseNode = "Rent.";
	
	/**
	 * Constructs a RentPermissionsManager using the given Rent plugin.
	 * @param plugin The plugin to use when getting necessary objects.
	 */
	public RentPermissionsManager(Rent plugin){
	    
	}
	
	/**
	 * This method returns whether or not a player has a given permission.<br/>
	 * If the given {@code CommandSender} is not a player they are assumed to be the console
	 * and are given <i><b>all</b></i> permissions.
	 * @param sender The {@code CommandSender} in question.
	 * @param node The {@code String} permissions node to test <b>WITHOUT</b> "Rent." internally "Rent."
     * is added to the start of the node.
	 * @return {@code true} the player has this permission. {@code false} otherwise.
	 */
	public boolean checkPermission(CommandSender sender,String node){
		if(sender instanceof Permissible){
			Permissible permble = (Permissible) sender;
			if(node==null){
				return false;
			}
			String workingNode = baseNode+node;
			return permble.hasPermission(workingNode);
		}
		else if(sender instanceof ConsoleCommandSender){
			return true; //Probably console.
		}
		return false;
	}
}
