package net.mcviral.dev.plugins.whoisthat.util;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

import org.bukkit.OfflinePlayer;

import net.mcviral.dev.plugins.whoisthat.main.WhoIsThat;

public class Converter{
	
	private WhoIsThat plugin = null;
	
	public Converter(WhoIsThat that){
		plugin = that;
		File f = new File(plugin.getDataFolder() + "/To Convert/");
		if (!f.exists()){
			f.mkdir();
		}
		if (plugin.getConfig().getBoolean("convert") == true){
			convertPlayers();
			//plugin.getConfig().set("convert", false);
			//plugin.saveConfig();
		}
		plugin.log.info(countPlayers() + " players on file.");
	}
	
	public void convertPlayers(){
		File folder = new File(plugin.getDataFolder() + "/Data/Players/");
		if (!folder.exists()){
			folder.mkdirs();
		}
		FileManager fm = null;
		File f = null;
		Long l = 0L;
		//plugin.log.info("");
		plugin.log.info("Starting conversion of players...");
		for (OfflinePlayer op : plugin.getServer().getOfflinePlayers()){
			f= new File(plugin.getDataFolder() + "/Data/Players/");
			if (!f.exists()){
				l = l + 1L;
				createYaml(plugin.getDataFolder() + "/Data/Players/" + op.getUniqueId().toString());
				fm = new FileManager(plugin, "Data/Players/", op.getUniqueId().toString());
				fm.getYAML().set("name", op.getPlayer().getName());
				fm.getYAML().set("firstPlayed", op.getFirstPlayed());
				fm.saveYAML();
			}
		}
		plugin.log.info(l + " players converted.");
	}
	
	public int countPlayers(){
		List <File> tempfiles = new LinkedList <File> ();
		File folder = new File(plugin.getDataFolder() + "/Data/Players/");
		if (folder.exists()){
			tempfiles = Arrays.asList(folder.listFiles());
			return tempfiles.size();
		}else{
			folder.mkdirs();
			plugin.log.info("'Players' folder not found, created one and skipped file check.");
			return -1;
		}
	}
	
	public void checkForFilesToConvert(){
		List <File> tempfiles = new LinkedList <File> ();
		LinkedList <File> files = new LinkedList <File> ();
		File folder = new File(plugin.getDataFolder() + "/To Convert/");
		if (folder.exists()){
			tempfiles = Arrays.asList(folder.listFiles());
			Long l = 0L;
			for (File f : tempfiles){
				if (f.getName().endsWith(".yml")){
					files.add(f);
					l = l + 1L;
				}
			}
			plugin.log.info(l + " files found for conversion.");
		}else{
			plugin.log.info("'To Convert' folder not found, created one and skipped file check.");
		}
	}
	
	public boolean isOnFile(UUID uuid){
		File folder = new File(plugin.getDataFolder() + "/Data/Players/");
		if (folder.exists()){
			File f = new File(plugin.getDataFolder() + "/Data/Players/" + uuid.toString() + ".yml");
			if (f.exists()){
				return true;
			}
			return false;
		}else{
			folder.mkdirs();
			return false;
		}
	}
	
	public String getOriginalName(UUID uuid){
		File folder = new File(plugin.getDataFolder() + "/Data/Players/");
		if (folder.exists()){
			File f = new File(plugin.getDataFolder() + "/Data/Players/" + uuid.toString() + ".yml");
			if (f.exists()){
				FileManager fm = new FileManager(plugin, "Data/Players/", uuid.toString());
				String name = null;
				try{
					name = fm.getYAML().getString("name");
				}catch(Exception e){
					e.printStackTrace();
				}
				return name;
			}
			return null;
		}else{
			folder.mkdirs();
			return null;
		}
	}
	
	public void createYaml(String name){
		try{ 
			File file = new File(name + ".yml");
			file.createNewFile(); 
		}catch(IOException e){ 
			e.printStackTrace(); 
		}
	}
	
	public void createFolder(String path){
		File f = new File(path);
		f.mkdirs();
	}
	
	public boolean folderExists(String path){
		File f = new File(path);
		if (f.exists()){
			return true;
		}
		return false;
	}
	
}
