package me.mani.panemgames.chest;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;

public class ChestManager {
	
	private static List<Location> allLocations = new ArrayList<>(); 
	
	public static void registerChest(Block b) {
		if (allLocations.contains(b.getLocation()))
			return;
		if (b != null && b.getType() != null && (b.getType() == Material.CHEST || b.getType() == Material.TRAPPED_CHEST)) {
			if (b.getType() == Material.CHEST) {
				WildChest wildChest = new WildChest(b.getLocation());
				wildChest.build(ChestType.WILD_CHEST);
			}
			else {
				SpawnChest spawnChest = new SpawnChest(b.getLocation());
				spawnChest.build(ChestType.SPAWN_CHEST);
			}
		}
		allLocations.add(b.getLocation());
	}
	
	public static boolean isRegistered(Block b) {
		return allLocations.contains(b.getLocation());
	}
	
	public static void unregisterChest(Block b) {
		allLocations.remove(b.getLocation());
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
