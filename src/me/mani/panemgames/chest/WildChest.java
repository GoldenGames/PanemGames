package me.mani.panemgames.chest;

import me.mani.panemgames.chest.ChestManager.ChestType;

import org.bukkit.Location;

public class WildChest extends PanemChest {

	public WildChest(Location loc) {
		super(loc, ChestType.WILD_CHEST);
	}

}
