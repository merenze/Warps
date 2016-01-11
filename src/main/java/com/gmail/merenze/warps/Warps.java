package com.gmail.merenze.warps;

import org.bukkit.plugin.java.JavaPlugin;

public class Warps extends JavaPlugin {
	@Override
	public void onEnable() {
		this.getCommand("warp").setExecutor(new WarpCommand(this));
		this.loadConfiguration();
	}
	//Adds a warp to configuration
	//Loads configuration files
	public void loadConfiguration() {
		this.getConfig().options().copyDefaults(true);
		this.saveConfig();
	}
}
