package me.mani.panemgames.chest;

import me.mani.panemgames.chest.ChestManager.ChestType;

import org.bukkit.Location;
import org.bukkit.block.Chest;

public abstract class PanemChest {
	
	private Location loc;
	
	public PanemChest(Location loc) {
		this.loc = loc;
	}
	
	public void build(ChestType type) {
		ChestBuilder builder = new ChestBuilder(this, type);
		builder.build();
	}
	
	public Location getLocation() {
		return this.loc;
	}

	public Chest getChestBlock() {
		return (Chest) loc.getBlock().getState();
	}
	
}
