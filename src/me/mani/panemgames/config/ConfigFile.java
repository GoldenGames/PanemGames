package me.mani.panemgames.config;

import java.io.File;

import org.bukkit.configuration.file.YamlConfiguration;

public abstract class ConfigFile {
	
	private File file;
	private YamlConfiguration cfg;
	
	public ConfigFile(String configName) {
		this.file = new File("plugins/PanemGames/" + configName + ".yml");
		this.cfg = YamlConfiguration.loadConfiguration(file);
		this.cfg.set("configFile", true);
	}

	public File getFile() {
		return file;
	}
	
	public YamlConfiguration getConfig() {
		return cfg;
	}
	
	public abstract void getAll();
	
	public abstract void setAll();
	
}
