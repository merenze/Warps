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
	private File yml;
	private YamlConfiguration config;
	/**
	 * Creates a new YAML file
	 * @param plugin
	 *    Plugin the file is being created for
	 * @param name
	 *    Name of the file; must include ".yml"
	 */
	public CustomConfig(JavaPlugin plugin, String filename) {
		this.yml = new File(plugin.getDataFolder() + File.separator + filename);
		this.config = YamlConfiguration.loadConfiguration(yml);
	}
	/**
	 * Saves the YAML file
	 */
	public void save() {
		try {
			config.save(yml);
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}
	/**
	 * Stores a value in the YAML file
	 * @param path
	 * @param value
	 */
	public void set(String path, Object value) {
		config.set(path, value);
	}
	
	public Object get(String path) {
		return config.get(path);
	}
	public int getInt(String path) {
		return config.getInt(path);
	}
	public double getDouble(String path) {
		return config.getDouble(path);
	}
	public boolean getChar(String path) {
		return config.getBoolean(path);
	}
	public String getString(String path) {
		return config.getString(path);
	}
	public List getList(String path) {
		return config.getList(path);
	}
	public Set<String> getKeys(boolean deep) {
		return config.getKeys(deep);
	}

	public ConfigurationSection getConfigurationSection(String path) {
		return config.getConfigurationSection(path);
	}
	public FileConfigurationOptions options() {
		return config.options();
	}
}
