package me.krotn.Rent;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;

public class RentDumpManager {
	private Rent plugin;
	private String fileName;
	private static final String defaultFileName = "dump.html";
	
	public RentDumpManager(Rent plugin){
		this(plugin,defaultFileName);
	}
	
	public RentDumpManager(Rent plugin, String fileName){
		this.plugin = plugin;
		this.fileName = RentDirectoryManager.getPathInDir(fileName);
	}
	
	public void dump() throws Exception{
		File file = new File(RentDirectoryManager.getPathInDir(fileName));
		if(file.exists()){
			file.delete();
		}
		BufferedWriter out = new BufferedWriter(new FileWriter(fileName));
		out.write("<HTML><HEAD><TITLE>Rent Dump</TITLE></HEAD><BODY>");//TODO: Close body and html and table
		out.write("<table border=\"1\"><tr><td>Player</td><td>Amount Owed</td>");
		ArrayList<Integer> players = plugin.getDatabaseManager().getPlayers();
		RentDatabaseManager dbMan = plugin.getDatabaseManager();
		RentCalculationsManager calcMan = plugin.getCalculationsManager();
		for(Integer player:players){
			out.write("<tr><td>"+dbMan.getPlayerFromID(player.intValue())+"</td><td>"+calcMan.getAmountPlayerOwes(player)+"</td></tr>");
		}
		out.write("</table></BODY></HTML>");
		out.flush();
		out.close();
	}
}
