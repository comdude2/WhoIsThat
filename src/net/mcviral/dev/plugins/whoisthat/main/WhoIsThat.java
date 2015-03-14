package net.mcviral.dev.plugins.whoisthat.main;

import net.mcviral.dev.plugins.whoisthat.util.Converter;
import net.mcviral.dev.plugins.whoisthat.util.Log;
import net.md_5.bungee.api.ChatColor;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class WhoIsThat extends JavaPlugin{
	
	public Log log = new Log(this.getName());
	public Converter converter = null;
	public Listeners listeners = null;
	public boolean loadedbefore = false;
	
	public void onEnable(){
		this.saveDefaultConfig();
		converter = new Converter(this);
		listeners = new Listeners(this, converter);
		if (!loadedbefore){
			this.getServer().getPluginManager().registerEvents(listeners, this);
		}else{
			log.info("This plugin has been reloaded, if you did this to update this plugin, in order for any newly added events to be registered you'll need to stop the server and start it again.");
		}
		this.getLogger().info(this.getDescription().getName() + " Enabled!");
	}
	
	public void onDisable(){
		this.getLogger().info(this.getDescription().getName() + " Disabled!");
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (cmd.getName().equalsIgnoreCase("whoisthat")){
			if (args.length > 0){
				if (args.length == 1){
					if (sender.hasPermission("whoisthat.inquire")){
						String name = args[0];
						boolean found = false;
						for (Player p : this.getServer().getOnlinePlayers()){
							if (p.getName().equalsIgnoreCase(name)){
								if (converter.isOnFile(p.getUniqueId())){
									String oldname = converter.getOriginalName(p.getUniqueId());
									if (oldname != null){
										sender.sendMessage(ChatColor.YELLOW + "Player: '" + ChatColor.BLUE + args[0] + ChatColor.YELLOW + "' was known by the name '" + ChatColor.BLUE + oldname + ChatColor.YELLOW + "' before.");
									}else{
										//Error
										sender.sendMessage(ChatColor.DARK_RED + "ERROR: Couldn't find player's previous name on file but the file exists, please report this to a member of staff.");
									}
								}else{
									sender.sendMessage(ChatColor.RED + "Player is not on file, he has no previous name to our knowledge.");
								}
								found = true;
								break;
							}
						}
						if (!found){
							sender.sendMessage(ChatColor.RED + "Player is not online (Perhaps it's a display name and not their actual name).");
						}
					}
				}else{
					help(sender);
				}
			}else{
				help(sender);
			}
			return true;
		}else{
			return false;
		}
	}
	
	public void help(CommandSender sender){
		sender.sendMessage(ChatColor.BLUE + "WhoIsThat Help:");
		sender.sendMessage(ChatColor.GREEN + "/whoisthat <playername>" + ChatColor.YELLOW + " Gets a players previous name (Player " + ChatColor.RED + "MUST" + ChatColor.YELLOW + " be online.");
	}
	
}
