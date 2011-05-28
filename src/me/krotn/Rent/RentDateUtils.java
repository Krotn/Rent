package me.krotn.Rent;

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
}
