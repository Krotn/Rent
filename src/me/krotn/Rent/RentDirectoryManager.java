package me.krotn.Rent;

import java.io.File;

public class RentDirectoryManager {
	
	private static final String mainDirectory = "plugins"+File.separator+"Rent";
	
	public static void createDirectory(){
		new File(mainDirectory).mkdir();
	}
	
	public static String getMainDirectory(){
		return mainDirectory;
	}
	
	public static String getPathInDir(String fileName){
		return mainDirectory+File.separator+fileName;
	}
}
