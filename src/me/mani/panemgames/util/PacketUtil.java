package me.mani.panemgames.util;

import java.lang.reflect.Field;

import net.minecraft.server.v1_7_R4.Packet;

import org.bukkit.craftbukkit.v1_7_R4.entity.CraftPlayer;
import org.bukkit.entity.Player;

public class PacketUtil {
	
	/**
	 * Sends a packet to a list of player
	 * 
	 * @param packet The packet to send
	 * @param allPlayer The player that will recieve the packet
	 */
	public static void sendPacket(Packet packet, Player... allPlayer) {
		for (Player p : allPlayer)
			((CraftPlayer) p).getHandle().playerConnection.sendPacket(packet);
	}
	
	/**
	 * Sends a packet to a list of player that have 1.8
	 * 
	 * @param packet The packet to send
	 * @param allPlayer The player that will recieve the packet (if they have 1.8)
	 */
	public static void sendNewPacket(Packet packet, Player... allPlayer) {
		for (Player p : allPlayer)
			if (((CraftPlayer) p).getHandle().playerConnection.networkManager.getVersion() >= 47)
				((CraftPlayer) p).getHandle().playerConnection.sendPacket(packet);
	}

	/**
	 * Sets a field in an packet
	 * 
	 * @param packet The packet where the field will be set
	 * @param name The name of the field
	 * @param value The value the field will become
	 */
	public static void setField(Packet packet, String name, Object value) {
		try {
			Field field = packet.getClass().getDeclaredField(name);
			field.setAccessible(true);
			field.set(packet, value);
		} catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Converts a double to a fixedPoint integer
	 * 
	 * @param d The double that will be converted
	 * @return The coverted fixedPoint integer
	 */
	public static int toFixedPoint(double d) {
		return (int)d * 32;
	}
	
	/**
	 * Converts a float to a packedByte byte
	 * 
	 * @param f The float that will be converted
	 * @return The converted packedByte byte
	 */
	public static byte toPackedByte(float f) {
		return (byte) ((int) (f * 256.0F / 360.0F));
	}

}
