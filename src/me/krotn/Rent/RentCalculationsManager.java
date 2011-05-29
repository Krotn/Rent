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
}
