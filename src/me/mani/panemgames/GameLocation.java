package me.mani.panemgames;

import java.io.Serializable;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;

public class GameLocation implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private double x;
	private double y;
	private double z;
	private float yaw;
	private float pitch;
	private String world;
	
	private String name;
	
	public GameLocation(final String name, final World world, final double x, final double y, final double z) {
        this(name, world, x, y, z, 0, 0);
    }
	
	public GameLocation(final String name, final Location loc) {
		this(name, loc.getWorld(), loc.getX(), loc.getY(), loc.getZ(), loc.getYaw(), loc.getPitch());
	}
	
	public GameLocation(final String name, final World world, final double x, final double y, final double z, final float yaw, final float pitch) {
		this.name = name;
		this.world = world.getName();
        this.x = x;
        this.y = y;
        this.z = z;
        this.pitch = pitch;
        this.yaw = yaw;
    }
	
	public double getX() {
		return this.x;
	}
	
	public double getY() {
		return this.y;
	}
	
	public double getZ() {
		return this.z;
	}
	
	public float getYaw() {
		return this.yaw;
	}
	
	public float getPitch() {
		return this.pitch;
	}
	
	public String getWorldName() {
		return this.world;
	}
	
	public Location getLocation() {
		return(new Location(Bukkit.getWorld(world), x, y, z, yaw, pitch));
	}
	
	public String getName() {
		return name;
	}

}
