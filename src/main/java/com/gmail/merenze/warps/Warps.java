package com.gmail.merenze.warps;

import org.bukkit.plugin.java.JavaPlugin;

import jellyrekt.CustomConfig;

public class Warps extends JavaPlugin {
	private CustomConfig playerWarps;
	
	@Override
	public void onEnable() {
		playerWarps = new CustomConfig(this,"playerWarps.yml");
		this.getCommand("warp").setExecutor(new WarpCommand(this));
		this.loadConfiguration();
		this.loadPlayerWarps();
	}
	//Adds a warp to configuration
	//Loads configuration files
	public void loadConfiguration() {
		this.getConfig().options().copyDefaults(true);
		this.saveConfig();
	}
	public void loadPlayerWarps() {
		playerWarps.getYaml().options().copyDefaults(true);
		playerWarps.save();
	}
	
	public CustomConfig getPlayerWarps() {
		return playerWarps;
	}
}
