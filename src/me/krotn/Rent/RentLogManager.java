package me.krotn.Rent;

import java.util.logging.Logger;

public class RentLogManager {
	private Logger log;
	private String prefix = "RENT: ";
	
	public RentLogManager(Logger log){
		this.log=log;
	}
	
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
