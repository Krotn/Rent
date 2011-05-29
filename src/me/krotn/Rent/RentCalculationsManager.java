package me.krotn.Rent;

public class RentCalculationsManager {
	Rent plugin;
	RentDatabaseManager dbMan;
	RentLogManager logMan;
	
	public RentCalculationsManager(Rent plugin){
		this.plugin = plugin;
		this.dbMan = this.plugin.getDatabaseManager();
		this.logMan = this.plugin.getLogManager();
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
