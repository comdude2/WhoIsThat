package net.mcviral.dev.plugins.whoisthat.main;

import net.mcviral.dev.plugins.whoisthat.util.Converter;
import net.md_5.bungee.api.ChatColor;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class Listeners implements Listener{
	
	private WhoIsThat plugin = null;
	private Converter converter = null;
	
	public Listeners(WhoIsThat that, Converter convert){
		plugin = that;
		converter = convert;
	}
	
	@EventHandler(priority = EventPriority.HIGHEST)
	public void onPlayerJoin(PlayerJoinEvent event){
		plugin.log.info("Checking if " + event.getPlayer().getName() + " has a Name file...");
		if (converter.isOnFile(event.getPlayer().getUniqueId())){
			plugin.log.info(event.getPlayer().getName() + "'s name file found!");
			String currentname = event.getPlayer().getName();
			String previousname = converter.getOriginalName(event.getPlayer().getUniqueId());
			if (previousname != null){
				if (currentname.equalsIgnoreCase(previousname)){
					plugin.log.info("Names were the same, ignoring.");
				}else{
					plugin.log.info("Names were different, notifying admins.");
					for (Player p : plugin.getServer().getOnlinePlayers()){
						p.sendMessage(ChatColor.YELLOW + "Player: '" + ChatColor.BLUE + currentname + ChatColor.YELLOW + "' was known by the name '" + ChatColor.BLUE + previousname + ChatColor.YELLOW + "' before.");
					}
				}
			}else{
				//Some sort of error
			}
		}else{
			plugin.log.info("No name file found for " + event.getPlayer().getName());
		}
	}
	
}
