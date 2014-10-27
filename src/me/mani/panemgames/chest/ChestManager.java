package me.mani.panemgames.chest;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Chest;

public class ChestManager {
	
	private static List<Location> allLocations = new ArrayList<>(); 
	
	public static void registerChest(Chest chest) {
		if (allLocations.contains(chest.getLocation()))
			return;
		if (chest.getType() == Material.CHEST)
			new WildChest(chest.getLocation()).build();
		else
			new SpawnChest(chest.getLocation()).build();
		allLocations.add(chest.getLocation());
	}
	
	public static boolean isRegistered(Chest chest) {
		return allLocations.contains(chest.getLocation());
	}
	
	public static void unregisterChest(Chest chest) {
		allLocations.remove(chest.getLocation());
	}
	
	public static void unregisterAll() {
		allLocations.clear();
	}
	
	public enum ChestType {
		SPAWN_CHEST (Material.TRAPPED_CHEST), 
		WILD_CHEST (Material.CHEST);
		
		private Material chestMaterial;
		
		private ChestType(Material material) {
			this.chestMaterial = material;
		}
		
		public Material getChestMaterial() {
			return this.chestMaterial;
		}
	}

}
