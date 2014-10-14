package me.mani.panemgames.config;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class ConfigManager {
	
	private List<ConfigFile> allConfigFiles = new ArrayList<>();
	
	public ConfigManager(ConfigFile... allConfigFiles) {
		this.allConfigFiles.addAll(Arrays.asList(allConfigFiles));
	}
	
	public void saveAll() {
		for (ConfigFile configFile : allConfigFiles) {
			configFile.setAll();
			try {
				configFile.getConfig().save(configFile.getFile());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void loadAll() {
		for (ConfigFile configFile : allConfigFiles) {
			configFile.getAll();
		}
	}

}
