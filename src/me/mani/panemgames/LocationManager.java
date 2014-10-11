package me.mani.panemgames;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

public class LocationManager {
	
private static List<GameLocation> allLocations = new ArrayList<>();
	
	public static GameLocation getLocation(String name) {
		for (Iterator<GameLocation> it = allLocations.iterator(); it.hasNext();) {
			GameLocation loc = it.next();
			if (loc.getName().equals(name))
				return loc;
		}
		return null;
	}
	
	public static GameLocation getSpawnLocations(int number) {
		for (Iterator<GameLocation> it = allLocations.iterator(); it.hasNext();) {
			GameLocation loc = it.next();
			if (loc.getName().equals("spawn" + number))
				return loc;
		}
		return null;
	}

	public static void add(GameLocation loc) {
		allLocations.add(loc);
	}
	
	public static void remove(GameLocation loc) {
		allLocations.remove(loc);
	}
	
	public static void saveAll() {
		File file = new File("plugins/PanemGames/locations.yml");
		YamlConfiguration cfg = YamlConfiguration.loadConfiguration(file);
		List<String> allLocationNames = new ArrayList<>();
		for (GameLocation loc : allLocations) {
			cfg = configureLocationDetails(cfg, loc);
			allLocationNames.add(loc.getName());
		}	
		cfg.set("all", allLocationNames);
		try {
			cfg.save(file);
		} catch (IOException e) {
			e.printStackTrace();
		}	
	}

	public static void loadAll() {
		FileConfiguration cfg = YamlConfiguration.loadConfiguration(new File("plugins/PanemGames/locations.yml"));
		for (String name : cfg.getStringList("all")) {
		    List<String> details = cfg.getStringList("locations." + name);
		    LocationManager.add(new GameLocation(name, Bukkit.getWorld(details.get(5)), Double.parseDouble(details.get(0)), Double.parseDouble(details.get(1)), Double.parseDouble(details.get(2)), Float.parseFloat(details.get(3)), Float.parseFloat(details.get(4))));
		}
	}
	
	private static YamlConfiguration configureLocationDetails(YamlConfiguration cfg, GameLocation loc) {
		List<String> details = new ArrayList<>();
		details.add("" + loc.getX());
		details.add("" + loc.getY());
		details.add("" + loc.getZ());
		details.add("" + loc.getYaw());
		details.add("" + loc.getPitch());
		details.add("" + loc.getWorldName());
		cfg.set("locations." + loc.getName(), details);
		return cfg;
	}
}
