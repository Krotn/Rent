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
	RentLogManager logManager = new RentLogManager(log);
	
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
			logManager.severe("Could not load SQLite database driver!");
		}
		try{
			conn = DriverManager.getConnection("jdbc:sqlite:"+RentDirectoryManager.getPathInDir(databaseName));
		}catch(SQLException e){
			logManager.severe("Could not establish database connection!");
		}
	}
	
	public void disconnect(){
		if(conn==null){
			return;
		}
		try{
			conn.close();
		}catch(SQLException e){
			logManager.severe("Could not close database connection!");
		}catch(Exception e){
			logManager.severe("Could not close database connection!");
		}
	}
	
	public boolean isConnected(){
		if(conn==null){
			return false;
		}
		try{
			if(conn.isClosed()){
				return false;
			}
		}catch(SQLException e){
			logManager.warning("Unable to determine if database is connected!");
		}
		return true;
	}
	
	public void setup(){
		if(!isConnected()){
			connect();
		}
		logManager.info("Setting up the database...");
		try{
			Statement statement = conn.createStatement();
			statement.executeUpdate("CREATE TABLE Months (id INTEGER PRIMARY KEY,ref TEXT,cost REAL);");
			statement.executeUpdate("CREATE TABLE Players (id INTEGER PRIMARY KEY,name TEXT);");
			statement.executeUpdate("CREATE TABLE Logins (id INTEGER PRIMARY KEY,player_id INTEGER,month_id INTEGER,FOREIGN KEY(player_id) REFERENCES Players(id),FOREIGN KEY (month_id) REFERENCES Months(id));");
		}catch(SQLException e){
			logManager.severe("Unable to set-up the database!");
		}
	}
}
