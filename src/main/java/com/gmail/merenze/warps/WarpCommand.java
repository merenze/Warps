package com.gmail.merenze.warps;

import java.util.Set;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class WarpCommand implements CommandExecutor {
	private Warps plugin;
	
	private final String usage = ChatColor.GRAY + "/warp <warpname>: Teleports you to your specified warp.\n";
	private final String setusage = ChatColor.GRAY + "/warp set <warpname>: Sets a warp at your location.\n";
	private final String deleteusage = ChatColor.GRAY + "/warp del <warpname>: Deletes your specified warp.\n";
	private final String listusage = ChatColor.GRAY + "/warp list: Lists your warps.\n";
	private final String nowarp = ChatColor.GRAY + "You do not have a warp by that name.";
	private final String warpset = ChatColor.GRAY + "Warp set to your location.";
	private final String warpdeleted = ChatColor.GRAY + "Warp deleted.";
	
	public WarpCommand(Warps plugin) {
		this.plugin = plugin;
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (sender instanceof Player) { //If sender is a player
			Player player = (Player) sender;
			if (args.length==0) { //If no arguments are provided
				player.sendMessage(usage+setusage+deleteusage+listusage); //Send usage message
			} else if (args.length==1) { //If one argument is provided
				if (args[0].equalsIgnoreCase("set")) { //If argument is "set"
					player.sendMessage(setusage); //Send usage message
				} else if (args[0].equalsIgnoreCase("delete")) { //If argument is "delete"
					player.sendMessage(deleteusage); //Send usage message
				} else if (args[0].equalsIgnoreCase("list")) { //If argument is "list"
					listWarps(player); //List player's warps
				} else { //If argument is a warp name
					if (hasWarp(player, args[0])) { //If the player has the warp
						player.teleport(getWarpLocation(player, args[0])); //Teleport player to warp
					} else { //If player does not have the warp
						player.sendMessage(nowarp); //Send error message
					}
				}
			} else if (args.length>=2) { //If two arguments are provided
				if (args[0].equalsIgnoreCase("set")) { //If first argument is "set"
					setWarp(player, args[1]); //Creates the warp
					player.sendMessage(warpset);
				} else if (args[0].equalsIgnoreCase("delete") || args[0].equalsIgnoreCase("del")) { //If first argument is "delete"
					if (hasWarp(player, args[1])) { //If warp exists
						deleteWarp(player, args[1]); //Delete warp
					player.sendMessage(warpdeleted);
					} else { //If warp doesn't exist
						player.sendMessage(nowarp);
					}
				} else if (args[0].equalsIgnoreCase("list")) { //If first argument is "list"
					listWarps(player);
				} else { //If player types a warp name
					if (hasWarp(player, args[0])) { //If the player has the warp
						player.teleport(getWarpLocation(player, args[0])); //Teleport player to warp
					} else { //If player does not have the warp
						player.sendMessage(nowarp); //Send error message
					}
				}
			}
		} else { //If sender is not a player
			sender.sendMessage(usage+setusage+deleteusage+listusage+ "You must be a player to use this command.");
		}
		return true;
	}
	//Gets the warp location
	public Location getWarpLocation(Player player, String warp) {
		if (hasWarp(player, warp)) { //Called again in case getWarpLocation is called outside of teleport method
			String uuid = player.getUniqueId().toString();
			World world = plugin.getServer().getWorld(plugin.getPlayerWarps().getYaml().getString(uuid + "." + warp + ".world"));
			double x = plugin.getPlayerWarps().getYaml().getDouble(uuid + "." + warp + ".x");
			double y = plugin.getPlayerWarps().getYaml().getDouble(uuid + "." + warp + ".y");
			double z = plugin.getPlayerWarps().getYaml().getDouble(uuid + "." + warp + ".z");
			return new Location(world, x, y, z);
		}
		else return player.getLocation();
	}
	//Lists warps to player
	public void listWarps(Player player) {
		Set<String> warps = plugin.getPlayerWarps().getYaml().getConfigurationSection(player.getUniqueId().toString()).getKeys(false);
		String msg = ChatColor.GRAY + "Your warps: ";
		for (String name:warps) {
			msg = msg.concat("[" + name + "] ");
		}
		player.sendMessage(msg);
	}
	//Saves warp to config
	public void setWarp(Player player, String warp) {
		String path = player.getUniqueId().toString() + "." + warp + ".";
		Location location = player.getLocation();
		plugin.getPlayerWarps().getYaml().set(path + "world", location.getWorld().getName());
		plugin.getPlayerWarps().getYaml().set(path + "x", location.getX());
		plugin.getPlayerWarps().getYaml().set(path + "y", location.getY());
		plugin.getPlayerWarps().getYaml().set(path + "z", location.getZ());
		plugin.getPlayerWarps().save();
		plugin.loadPlayerWarps();
	}
	//Deletes warp from config
	public void deleteWarp(Player player, String warp) {
		String path = player.getUniqueId().toString() + "." + warp;
		plugin.getPlayerWarps().getYaml().set(path, null);
	}
	//Tests if player has warp by specified name
	public boolean hasWarp(Player player, String warp) {
		String uuid = player.getUniqueId().toString();
		if (plugin.getPlayerWarps().getYaml().getConfigurationSection("").contains(uuid)) { //If player is registered in config
			return plugin.getPlayerWarps().getYaml().getConfigurationSection(uuid).contains(warp); //Return true if player has warp, else return false
		} else  {
			return false; //If player is not registered in config, he has no warps. Return false
		}
	}
}
