package me.mani.panemgames.config;

import java.io.File;

import org.bukkit.configuration.file.YamlConfiguration;

public class ConfigManager {
	
	private static String defaultPath;
	private static String defaultEnding;
	private static YamlConfiguration config;
	
	static {
		defaultPath = "plugins/PanemGames/";
		defaultEnding = ".yml";
		config = YamlConfiguration.loadConfiguration(new File("plugins/PanemGames/config.yml"));
		addDefaults(config);
		if (config.getString("defaultPath") != null)
			defaultPath = config.getString("defaultPath");
		if (config.getString("defaultEnding") != null)
			defaultEnding = config.getString("defaultEnding");
	}
	
	private static void addDefaults(YamlConfiguration config) {
		config.addDefault("defaultPath", defaultPath);
		config.addDefault("defaultEnding", defaultEnding);
	}
	
	public static String getDefaultPath() {
		return defaultPath;
	}
	
	public static String getDefaultEnding() {
		return defaultEnding;
	}
	
	public static File getFileByDefaults(String fileName) {
		return new File(defaultPath + fileName + defaultEnding);
	}
}
