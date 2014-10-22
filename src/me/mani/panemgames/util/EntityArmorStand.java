package me.mani.panemgames.util;

import java.util.Arrays;
import java.util.List;

import me.mani.panemgames.PlayerManager;
import net.minecraft.server.v1_7_R4.DataWatcher;
import net.minecraft.server.v1_7_R4.PacketPlayOutEntityDestroy;
import net.minecraft.server.v1_7_R4.PacketPlayOutEntityMetadata;
import net.minecraft.server.v1_7_R4.PacketPlayOutSpawnEntity;

import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_7_R4.CraftWorld;
import org.bukkit.entity.Player;

public class EntityArmorStand {
	
	private int entityId;
	private Location loc;
	
	private String customName;
	private boolean customNameVisible;
	private boolean visible;
	
	public EntityArmorStand(Location loc) {
		this.loc = loc;
		this.entityId = ((CraftWorld) loc.getWorld()).getHandle().entityList.size() + 1;
	}

	public void spawn() {	
		spawn(PlayerManager.getAll());
	}
	
	public void spawn(Player... players) {
		spawn(Arrays.asList(players));
	}
	
	public void spawn(List<Player> players) {
		PacketPlayOutSpawnEntity armorStand = new PacketPlayOutSpawnEntity();
		
		PacketUtil.setField(armorStand, "a", entityId);
		PacketUtil.setField(armorStand, "j", (byte) 78);
		PacketUtil.setField(armorStand, "b", PacketUtil.toFixedPoint(loc.getX()));
		PacketUtil.setField(armorStand, "c", PacketUtil.toFixedPoint(loc.getY()));
		PacketUtil.setField(armorStand, "d", PacketUtil.toFixedPoint(loc.getZ()));
		PacketUtil.setField(armorStand, "h", PacketUtil.toPackedByte(loc.getYaw()));
		PacketUtil.setField(armorStand, "i", PacketUtil.toPackedByte(loc.getYaw()));	
		PacketUtil.setField(armorStand, "k", 0);
		
		PacketUtil.sendNewPacket(armorStand, (Player[]) players.toArray());
		
		if (customName != null)
			updateMetadata(2, customName);
		
		updateMetadata(3, customNameVisible ? (byte) 1 : (byte) 0);
		
		if (!visible)
			updateMetadata(0, (byte) 0x20);
		
		updateMetadata(10, (byte) 0x03);
	}
	
	public void setCustomName(String name) {
		customName = name;
	}
	
	public String getCustomName() {
		return customName;
	}
	
	public void setCustomVisible(boolean visible) {
		customNameVisible = visible;
	}
	
	public boolean getCustomNameVisible() {
		return customNameVisible;
	}
	
	public void setVisible(boolean visible) {
		this.visible = visible;
	}
	
	public boolean getVisible() {
		return visible;
	}
	
	private void updateMetadata(int index, Object value) {
		DataWatcher data = new DataWatcher(null);
		data.a(index, value);
		
		PacketPlayOutEntityMetadata meta = new PacketPlayOutEntityMetadata(entityId, data, true);
		
		PacketUtil.sendNewPacket(meta, (Player[]) PlayerManager.getAll().toArray());
	}
	
	public void remove() {
		PacketPlayOutEntityDestroy despawn = new PacketPlayOutEntityDestroy(entityId);
		
		PacketUtil.sendNewPacket(despawn, (Player[]) PlayerManager.getAll().toArray());
	}
	
}
