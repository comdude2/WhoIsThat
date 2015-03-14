package net.mcviral.dev.plugins.whoisthat.util;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import net.mcviral.dev.plugins.whoisthat.main.WhoIsThat;

public class Converter{
	
	private WhoIsThat plugin = null;
	
	public Converter(WhoIsThat that){
		plugin = that;
		if (plugin.getConfig().getBoolean("convert") == true){
			//File f = new File("");
			//readDAT(new File(f.getAbsoluteFile() + "/world/playerdata/bc67e253-ddee-4fc9-8675-97943b6ab0a3.dat"));
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
			f= new File(plugin.getDataFolder() + "/Data/Players/" + op.getUniqueId().toString() + ".yml");
			if (!f.exists()){
				l = l + 1L;
				if (op.getUniqueId() != null){
					if ((op.getName() != null) && (op.getFirstPlayed() != 0)){
						createYaml(plugin.getDataFolder() + "/Data/Players/" + op.getUniqueId().toString());
						fm = new FileManager(plugin, "Data/Players/", op.getUniqueId().toString());
						fm.getYAML().set("name", op.getName());
						fm.getYAML().set("firstPlayed", op.getFirstPlayed());
						fm.saveYAML();
					}else{
						plugin.log.info("Couldn't convert an offline player as they had a NULL Player name or first join.");
						if (op.getName() != null){
							plugin.log.info("Player name: " + op.getName());
						}
						//break;
					}
				}else{
					plugin.log.info("Couldn't convert an offline player as they had a NULL UUID.");
					if (op.getName() != null){
						plugin.log.info("Player name: " + op.getName());
					}
					//break;
				}
			}else{
				//There was already a .yml for this player
			}
		}
		for (Player op : plugin.getServer().getOnlinePlayers()){
			f= new File(plugin.getDataFolder() + "/Data/Players/" + op.getUniqueId().toString() + ".yml");
			if (!f.exists()){
				l = l + 1L;
				if (op.getUniqueId() != null){
					if ((op.getName() != null) && (op.getFirstPlayed() != 0)){
						createYaml(plugin.getDataFolder() + "/Data/Players/" + op.getUniqueId().toString());
						fm = new FileManager(plugin, "Data/Players/", op.getUniqueId().toString());
						fm.getYAML().set("name", op.getName());
						fm.getYAML().set("firstPlayed", op.getFirstPlayed());
						fm.saveYAML();
					}else{
						plugin.log.info("Couldn't convert a player as they had a NULL Player name or first join.");
						if (op.getName() != null){
							plugin.log.info("Player name: " + op.getName());
						}
						//break;
					}
				}else{
					plugin.log.info("Couldn't convert a player as they had a NULL UUID.");
					if (op.getName() != null){
						plugin.log.info("Player name: " + op.getName());
					}
					//break;
				}
			}else{
				//There was already a .yml for this player
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
					plugin.log.info("Name found: " + name);
					return name;
				}catch(Exception e){
					e.printStackTrace();
				}
				return null;
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
