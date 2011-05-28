package me.krotn.Rent;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * This class provides several convenience methods for working with dates.
 * It will be used extensively by the rent plugin.
 *
 */
public class RentDateUtils {
	RentDatabaseManager dbMan;
	
	/**
	 * Constructs a {@code RentDateUtils} that will use the given {@code RentDatabaseManager}.
	 * @param dbMan The {@code RentDatabaseManager} to use.
	 */
	public RentDateUtils(RentDatabaseManager dbMan){
		this.dbMan = dbMan;
	}
	
	/**
	 * Returns the current human-readable date {@code String} (eg. "jan11").
	 * @return The current human readable date identification {@code String}.
	 */
	public String getCurrentMonth(){
		SimpleDateFormat dateFormat = new SimpleDateFormat("MMMyy");
		Date currentDate = new Date();
		String toReturn = dateFormat.format(currentDate);
		return toReturn.toLowerCase();
	}
}
