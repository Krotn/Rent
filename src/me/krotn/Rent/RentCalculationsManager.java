package me.krotn.Rent;

/**
 * This class manages the various calculations required for the Rent plugin.
 *
 */
public class RentCalculationsManager {
	Rent plugin;
	RentDatabaseManager dbMan;
	RentLogManager logMan;
	RentDateUtils dateUtils;
	
	/**
	 * Constructs a RentCalculationsManager.
	 * @param plugin The {@code Rent} plugin object used to get the various other objects 
	 * needed to calculated the required values.
	 */
	public RentCalculationsManager(Rent plugin){
		this.plugin = plugin;
		this.dbMan = this.plugin.getDatabaseManager();
		this.logMan = this.plugin.getLogManager();
		this.dateUtils = this.plugin.getDateUtils();
	}
	
	/**
	 * Returns the amount an individual player would owe had they logged in during the given month.
	 * @param monthID The {@code int} database ID of the requested month.
	 * @return The {@code double} amount a player would owe for logging in during the given month.
	 */
	public double getIndividualRate(int monthID){
		if(!dbMan.monthExists(dbMan.getMonthFromID(monthID))){
			return 0;
		}
		int numPlayersWhoLoggedIn = dbMan.getPlayersWhoLoggedIn(monthID).size();
		double monthlyCost = dbMan.getMonthCost(monthID);
		return monthlyCost/numPlayersWhoLoggedIn;
	}
}
