package me.mani.panemgames.holograms;

import me.mani.panemgames.util.EntityArmorStand;

import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Horse;
import org.bukkit.entity.Player;
import org.bukkit.entity.WitherSkull;
import org.bukkit.util.Vector;

public class HologramLine {
	
	private Horse horse;
	private WitherSkull skull;
	private EntityArmorStand armorStand;
	private String text;
	private Location location;
	
	public HologramLine(String text, Location loc) {	
		this.location = loc.clone().add(0, 56, 0);
		this.text = text;
		
		spawn();
	}

	private void spawn() {
		horse = (Horse) location.getWorld().spawnEntity(location, EntityType.HORSE);
		horse.setAge(-1700000);
		horse.setAgeLock(true);		
		horse.setCustomName(text);
		horse.setCustomNameVisible(true);
		horse.setNoDamageTicks(Integer.MAX_VALUE);
		
		skull = (WitherSkull) location.getWorld().spawnEntity(location, EntityType.WITHER_SKULL);
		skull.setDirection(new Vector(0, 0, 0));
		
		skull.setPassenger(horse);
		
		armorStand = new EntityArmorStand(location.clone().subtract(0, 57, 0));
		armorStand.setCustomName(text);
		armorStand.setCustomVisible(true);
		armorStand.setVisible(false);
		armorStand.spawn();
	}
	
	public Location getLocation() {
		return this.location;
	}
	
	public String getText() {
		return this.text;
	}
	
	public void setText(String newText) {
		this.text = newText;
		horse.setCustomName(newText);
		armorStand.setCustomName(newText);
	}
	
	public void move(double newY) {
		this.location.setY(newY);
		remove();
		spawn();
	}
	
	public void remove() {
		this.horse.remove();
		this.skull.remove();
		this.armorStand.remove();
	}
	
	public void sendArmorStand(Player p) {
		this.armorStand.spawn(p);
	}

}
