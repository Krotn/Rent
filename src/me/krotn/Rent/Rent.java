package me.krotn.Rent;

import java.util.logging.Logger;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
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
	private RentDateUtils dateUtils = new RentDateUtils(this,dbman);
	private RentCalculationsManager calcMan = new RentCalculationsManager(this);
	private RentPermissionsManager permMan = null;
	private PlayerListener playerListener = null;
	
	public void onEnable(){
		logManager.info("Rent enabled");
		PluginManager pluginManager = getServer().getPluginManager();
		dbman.connect();
		if(!propman.fileExists()){
			propman.setup();
		}
		permMan = new RentPermissionsManager(this);
		playerListener = new RentPlayerListener(this);
		pluginManager.registerEvent(Event.Type.PLAYER_LOGIN, playerListener, Priority.Normal, this);
		pluginManager.registerEvent(Event.Type.PLAYER_JOIN, playerListener, Priority.Normal, this);
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command command, String commandLabel, String[] args){
		dateUtils.sanityCheck();
		Player player = (Player) sender;
		if(!(sender instanceof Player)){
			logManager.warning("Cannot use commands through console!");
			return false;
		}
		if(!command.getName().equalsIgnoreCase("rent")){
			return true; //No further need of this.
		}
		if(args.length<1){
			return false;
		}
		if(args[0].equalsIgnoreCase("info")){
			//Execute info command
			if(args.length == 1 && permMan.checkPermission(player, "info.self")){
				//This is an info on oneself.
				if(dbman.playerExists(player.getName())){
					player.sendRawMessage(ChatColor.AQUA+propman.getProperty("selfInfoMessage")+" "+ChatColor.GREEN+propman.getProperty("currencyPrefix")+calcMan.getAmountPlayerOwes(dbman.getPlayerID(player.getName())));
					//Above line sends player the amount they owe.
					return true;
				}
			}
			else if(permMan.checkPermission(player, "info.other")&&args.length>1){
				//This is not a self request
				String requestedPlayer = args[1];
				if(dbman.playerExists(requestedPlayer)){
					int playerID = dbman.getPlayerID(requestedPlayer);
					String currencyPrefix = propman.getProperty("currencyPrefix");
					double playerOwes = calcMan.getAmountPlayerOwes(playerID);
					player.sendRawMessage(ChatColor.AQUA+requestedPlayer+": "+ChatColor.GREEN+currencyPrefix+new Double(playerOwes).toString());
					return true;
				}
				else{
					player.sendRawMessage(ChatColor.RED+"No such player!");
					return true;
				}
			}
		}//End of "info" command block.
		if(args[0].equalsIgnoreCase("addpayment")&&permMan.checkPermission(player, "payment.add")&&args.length>=3){
			String requestedPlayer = args[1];
			double amount = new Double(args[2]).doubleValue();
			if(amount<0){
				player.sendRawMessage(ChatColor.RED+"Amount must be positive!");
				return true;
			}
			if(!dbman.playerExists(requestedPlayer)){
				player.sendRawMessage(ChatColor.RED+"No such player!");
				return true;
			}
			dbman.addPlayerPayments(dbman.getPlayerID(requestedPlayer), amount);
			player.sendRawMessage(ChatColor.GREEN+"Added: "+amount+" to player: "+requestedPlayer+".");
			return true;
		}
		if(args[0].equalsIgnoreCase("dedpayment")&&permMan.checkPermission(player, "payment.deduct")&&args.length>=3){
			String requestedPlayer = args[1];
			double amount = new Double(args[2]).doubleValue();
			if(amount<0){
				player.sendRawMessage(ChatColor.RED+"Amount must be positive!");
				return true;
			}
			if(!dbman.playerExists(requestedPlayer)){
				player.sendRawMessage(ChatColor.RED+"No such player!");
				return true;
			}
			dbman.subtractPlayerPayments(dbman.getPlayerID(requestedPlayer),amount);
			player.sendRawMessage(ChatColor.GREEN+"Subtracted: "+amount+" from player: "+requestedPlayer+".");
			return true;
		}
		if(args[0].equalsIgnoreCase("adjmonth")&&permMan.checkPermission(player,"month.adjustcost")&&args.length>=3){
			String requestedMonth = args[1];
			double newCost = new Double(args[2]).doubleValue();
			if(!dbman.monthExists(requestedMonth)){
				player.sendRawMessage(ChatColor.RED+"No such month!");
				return true;
			}
			dbman.setMonthCost(dbman.getMonthID(requestedMonth), newCost);
			player.sendRawMessage(ChatColor.GREEN+"Set cost for "+requestedMonth+" to: "+newCost+".");
			return true;
		}
		if(args[0].equalsIgnoreCase("dump")&&permMan.checkPermission(player,"dump")){
			try{
				RentDumpManager dumpMan = new RentDumpManager(this);
				dumpMan.dump();
				player.sendRawMessage(ChatColor.GREEN+"Dump produced!");
				return true;
			}catch(Exception e){
				player.sendRawMessage(ChatColor.RED+"Error producing dump!");
				e.printStackTrace();
				return true;
			}
		}
		return false;
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
