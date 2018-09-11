package jellyrekt;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Set;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfigurationOptions;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;
/**
 * Wrapper class for org.bukkit.configuration.file.FileConfiguration
 * @author JellyRekt
 *
 */
public class CustomConfig {
	private File file;
	private YamlConfiguration config;
	/**
	 * Creates a new YAML file
	 * @param plugin
	 *    Plugin the file is being created for
	 * @param name
	 *    Name of the file; must include ".yml"
	 */
	public CustomConfig(JavaPlugin plugin, String filename) {
		this.file = new File(plugin.getDataFolder() + File.separator + filename);
		this.config = YamlConfiguration.loadConfiguration(file);
	}
	/**
	 * Saves the YAML file
	 */
	public void save() {
		try {
			config.save(file);
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}
	/**
	 * Returns the YamlConfiguration for the CustomConfig
	 */
	public YamlConfiguration getYaml() {
		return config;
	}
}
