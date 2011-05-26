package me.krotn.Rent;

import java.util.logging.Logger;

/**
 * This class applies standard formatting to all log messages it handles.
 * Currently, it adds the text "RENT :" to the beginning of each log message.
 */
public class RentLogManager {
	private Logger log;
	private String prefix = "RENT: ";
	
	/**
	 * Constructs a {@code RentLogManager} that uses the specified {@code Logger}
	 * @param log The {@code Logger} to use.
	 */
	public RentLogManager(Logger log){
		this.log=log;
	}
	
	/**
	 * Constructs a {@code RentLogManager} that uses the specified {@code Logger} and applies
	 * the specified prefix.
	 * @param log The logger to use.
	 * @param prefix The prefix to add to the beginning of each log message.
	 */
	public RentLogManager(Logger log, String prefix){
		this(log);
		this.prefix = prefix;
	}
	
	public String formatMessage(String message){
		return prefix+message;
	}
	
	public void info(String message){
		log.info(formatMessage(message));
	}
	
	public void warning(String message){
		log.warning(formatMessage(message));
	}
	
	public void severe(String message){
		log.severe(formatMessage(message));
	}
	
	public Logger getLogger(){
		return log;
	}
	
	public String getPrefix(){
		return prefix;
	}
}
