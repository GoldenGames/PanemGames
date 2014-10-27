package me.mani.panemgames.chest;

import me.mani.panemgames.chest.ChestManager.ChestType;

import org.bukkit.Location;
import org.bukkit.block.Chest;

public abstract class PanemChest {
	
	private Location loc;
	private ChestType type;
	
	public PanemChest(Location loc, ChestType type) {
		this.loc = loc;
		this.type = type;
	}
	
	public void build() {
		new ChestBuilder(this, this.type).build();
	}
	
	public Location getLocation() {
		return this.loc;
	}

	public Chest getChestBlock() {
		return (Chest) loc.getBlock().getState();
	}
	
	public ChestType getType() {
		return this.type;
	}
	
}
