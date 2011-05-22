package me.krotn.Rent;

import java.io.File;
import java.sql.*;
import java.util.logging.Logger;

public class RentDatabaseManager {
	private final String defaultDatabaseName = "rent.db";
	private String databaseName;
	private Connection conn = null;
	private String maindir = "plugins/Rent";
	Logger log = Logger.getLogger("Minecraft");
	
	public RentDatabaseManager(){
		this.databaseName = defaultDatabaseName;
	}
	
	public RentDatabaseManager(String databaseName){
		this.databaseName = databaseName;
	}
	
	public void connect(){
		if(conn!=null){
			return;
		}
		RentDirectoryManager.createDirectory();
		try{
			Class.forName("org.sqlite.JDBC");
		}catch(ClassNotFoundException e){
			log.severe("RENT: Could not load SQLite database driver!");
		}
		try{
			conn = DriverManager.getConnection("jdbc:sqlite:"+RentDirectoryManager.getPathInDir(databaseName));
		}catch(SQLException e){
			log.severe("RENT: Could not establish database connection!");
		}
	}
	
	public void disconnect(){
		if(conn==null){
			return;
		}
		try{
			conn.close();
		}catch(SQLException e){
			log.severe("RENT: Could not close database connection!");
		}catch(Exception e){
			log.severe("RENT: Could not close database connection!");
		}
	}
}
