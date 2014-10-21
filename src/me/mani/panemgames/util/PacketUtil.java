package me.mani.panemgames.util;

import java.lang.reflect.Field;

import net.minecraft.server.v1_7_R4.Packet;

import org.bukkit.craftbukkit.v1_7_R4.entity.CraftPlayer;
import org.bukkit.entity.Player;

public class PacketUtil {
	
	public static void sendPacket(Packet packet, Player... allPlayer) {
		for (Player p : allPlayer)
			((CraftPlayer) p).getHandle().playerConnection.sendPacket(packet);
	}
	
	public static void sendNewPacket(Packet packet, Player... allPlayer) {
		for (Player p : allPlayer)
			if (((CraftPlayer) p).getHandle().playerConnection.networkManager.getVersion() >= 47)
				((CraftPlayer) p).getHandle().playerConnection.sendPacket(packet);
	}

	public static void setField(Packet packet, String name, Object value) {
		try {
			Field field = packet.getClass().getDeclaredField(name);
			field.setAccessible(true);
			field.set(packet, value);
		} catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException e) {
			e.printStackTrace();
		}
	}
	
	public static int toFixedPoint(double d) {
		return (int)d * 32;
	}
	
	public static byte toPackedByte(float f) {
		return (byte) ((int) (f * 256.0F / 360.0F));
	}

}
