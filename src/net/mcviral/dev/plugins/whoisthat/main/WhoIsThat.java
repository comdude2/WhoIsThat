package net.mcviral.dev.plugins.whoisthat.main;

import net.mcviral.dev.plugins.whoisthat.util.Converter;
import net.mcviral.dev.plugins.whoisthat.util.Log;

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
	
}
