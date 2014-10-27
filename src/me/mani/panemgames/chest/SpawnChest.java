package me.mani.panemgames.chest;

import me.mani.panemgames.chest.ChestManager.ChestType;

import org.bukkit.Location;

public class SpawnChest extends PanemChest {

	public SpawnChest(Location loc) {
		super(loc, ChestType.SPAWN_CHEST);
	}

}
