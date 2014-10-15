package me.mani.panemgames.config;

import java.io.File;
import java.io.IOException;

import me.mani.panemgames.PanemGames;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

public class ConfigManager {
	
	private static String defaultPath;
	private static String defaultEnding;
	private static FileConfiguration config;
	
	public ConfigManager(PanemGames pl) {
		defaultPath = "plugins/PanemGames/";
		defaultEnding = ".yml";
		config = pl.getConfig();
		addDefaults(config);
		if (config.getString("defaultPath") != null)
			defaultPath = config.getString("defaultPath");
		if (config.getString("defaultEnding") != null)
			defaultEnding = config.getString("defaultEnding");
		config.options().copyDefaults(true);
		pl.saveConfig();
	}
	
	private static void addDefaults(FileConfiguration config) {
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
