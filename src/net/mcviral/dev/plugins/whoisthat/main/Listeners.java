package net.mcviral.dev.plugins.whoisthat.main;

import net.mcviral.dev.plugins.whoisthat.util.Converter;
import net.md_5.bungee.api.ChatColor;

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
		if (converter.isOnFile(event.getPlayer().getUniqueId())){
			String currentname = event.getPlayer().getName();
			String previousname = converter.getOriginalName(event.getPlayer().getUniqueId());
			if (previousname != null){
				plugin.getServer().broadcast(ChatColor.YELLOW + "Player: '" + ChatColor.BLUE + currentname + ChatColor.YELLOW + "' was known by the name '" + ChatColor.BLUE + previousname + ChatColor.YELLOW + "' before.", "");
			}else{
				//Some sort of error
			}
		}
	}
	
}
