package me.mani.panemgames.util;

import me.mani.panemgames.PlayerManager;
import net.minecraft.server.v1_7_R4.PacketPlayOutSpawnEntity;

import org.bukkit.Location;
import org.bukkit.entity.Player;

public class EntityArmorStand {
	
	private Location loc;
	
	public EntityArmorStand(Location loc) {
		this.loc = loc;
	}

	public void spawn() {
		PacketPlayOutSpawnEntity armorStand = new PacketPlayOutSpawnEntity();
		
		PacketUtil.setField(armorStand, "a", 10000);
		PacketUtil.setField(armorStand, "b", (byte) 30);
		PacketUtil.setField(armorStand, "c", PacketUtil.toFixedPoint(loc.getX()));
		PacketUtil.setField(armorStand, "d", PacketUtil.toFixedPoint(loc.getY()));
		PacketUtil.setField(armorStand, "e", PacketUtil.toFixedPoint(loc.getZ()));
		PacketUtil.setField(armorStand, "f", PacketUtil.toPackedByte(loc.getYaw()));
		PacketUtil.setField(armorStand, "g", PacketUtil.toPackedByte(loc.getYaw()));	
		PacketUtil.setField(armorStand, "h", 0);
		
		PacketUtil.sendNewPacket(armorStand, (Player[]) PlayerManager.getAll().toArray());
	}
	
}
