package me.krotn.Rent;

import java.io.File;
import java.sql.*;
import java.util.ArrayList;
import java.util.logging.Logger;

public class RentDatabaseManager {
	private static final String defaultDatabaseName = "rent.db";
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
		if(!isSetup()){
			setup();
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
			statement.executeUpdate("CREATE TABLE Months (id INTEGER PRIMARY KEY AUTOINCREMENT,ref TEXT,cost REAL);");
			statement.executeUpdate("CREATE TABLE Players (id INTEGER PRIMARY KEY AUTOINCREMENT,name TEXT);");
			statement.executeUpdate("CREATE TABLE Logins (id INTEGER PRIMARY KEY AUTOINCREMENT,player_id INTEGER,month_id INTEGER,FOREIGN KEY(player_id) REFERENCES Players(id),FOREIGN KEY (month_id) REFERENCES Months(id));");
		}catch(SQLException e){
			logManager.severe("Unable to set-up the database!");
		}
	}
	
	public boolean isSetup(){
		if(!isConnected()){
			connect();
		}
		try{
			Statement statement = conn.createStatement();
			ResultSet resultSet = statement.executeQuery("SELECT name FROM sqlite_master WHERE type='table' ORDER BY name;");
			ArrayList<String> results = new ArrayList<String>();
			while(resultSet.next()){
				results.add(resultSet.getString("name"));
			}
			resultSet.close();
			return results.contains("Months")&&results.contains("Players")&&results.contains("Logins");
		}catch(SQLException e){
			logManager.severe("Could not check if the database is set-up!");
			return false;
		}
	}
	public void addPlayer(String userName){
		String workingUserName = userName.toLowerCase();
		try{
			PreparedStatement statement = conn.prepareStatement("insert into \"Players\" (name) values (?);");
			statement.setString(1,workingUserName);
			statement.addBatch();
			conn.setAutoCommit(false);
			statement.executeBatch();
			conn.setAutoCommit(true);
		}catch(SQLException e){
			logManager.severe("Could not add player \""+workingUserName+"\" to the database!");
		}
	}
	
	public int getPlayerID(String userName){
		String workingUserName = userName.toLowerCase();
		try{
			Statement statement = conn.createStatement();
			ResultSet resultSet = statement.executeQuery("SELECT id FROM Players WHERE name=\""+workingUserName+"\";");
			if(!resultSet.isBeforeFirst()){
				resultSet.close();
				return -1;
			}
			int ID = resultSet.getInt("id");
			resultSet.close();
			return ID;
		}catch(SQLException e){
			logManager.severe("Could not get player ID for: "+workingUserName+"!");
			e.printStackTrace();
			return -1;
		}
	}
	
	public String getPlayerFromID(int id){
		try{
			Statement statement = conn.createStatement();
			ResultSet resultSet = statement.executeQuery("SELECT name FROM Players WHERE id="+id+";");
			if(!resultSet.isBeforeFirst()){
				resultSet.close();
				return null;
			}
			String name = resultSet.getString("name").toLowerCase();
			resultSet.close();
			return name;
		}catch(SQLException e){
			logManager.severe("Could not get player from ID: "+new Integer(id).toString()+".");
		}
		return null;
	}
	
	public boolean playerExists(String userName){
		String workingUserName = userName.toLowerCase();
		if(getPlayerID(workingUserName) == -1){
			return false;
		}
		return true;
	}
}
