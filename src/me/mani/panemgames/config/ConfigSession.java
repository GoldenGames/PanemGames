package me.mani.panemgames.config;

import java.io.File;
import java.io.IOException;

import org.bukkit.configuration.file.YamlConfiguration;

public class ConfigSession {
	
	private File file;
	private YamlConfiguration config;
	
	public ConfigSession(String fileName) {
		this(ConfigManager.getFileByDefaults(fileName));
	}
	
	public ConfigSession(File fromFile) {
		file = fromFile;
		config = YamlConfiguration.loadConfiguration(file);
		config.options().header("Created with ConfigSession, PanemGames (1999mani)");
		config.options().copyHeader(true);
		config.options().copyDefaults(true);
	}
	
	public YamlConfiguration getConfig() {
		return config;
	}
	
	public void save() {
		try {
			config.save(file);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
