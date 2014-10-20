package me.mani.panemgames.util;

import java.lang.reflect.Field;

import me.mani.panemgames.PlayerManager;
import net.minecraft.server.v1_7_R4.DataWatcher;
import net.minecraft.server.v1_7_R4.Packet;
import net.minecraft.server.v1_7_R4.PacketPlayOutBed;
import net.minecraft.server.v1_7_R4.PacketPlayOutEntityTeleport;
import net.minecraft.server.v1_7_R4.PacketPlayOutNamedEntitySpawn;
import net.minecraft.util.com.mojang.authlib.GameProfile;

import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.craftbukkit.v1_7_R4.entity.CraftPlayer;
import org.bukkit.entity.Player;

public class FakePlayer {
	
	private Location loc;
	private PacketPlayOutNamedEntitySpawn fakePlayer;
	
	public FakePlayer(OfflinePlayer p, Location loc) {
		this.loc = loc;
		
		fakePlayer = new PacketPlayOutNamedEntitySpawn();
		DataWatcher data = new DataWatcher(null);
		data.a(6, (float) 20);
		
		setField(fakePlayer, "a", 10000);
		setField(fakePlayer, "b", new GameProfile(p.getUniqueId(), p.getName()));
		setField(fakePlayer, "c", toFixedPoint(loc.getX()));
		setField(fakePlayer, "d", toFixedPoint(loc.getY()));
		setField(fakePlayer, "e", toFixedPoint(loc.getZ()));
		setField(fakePlayer, "f", toPackedByte(0f));
		setField(fakePlayer, "g", toPackedByte(0f));
		setField(fakePlayer, "h", 0);
		setField(fakePlayer, "i", data);
	}	
	
	public void spawn() {
		sendPacket(fakePlayer, (Player[]) PlayerManager.getAll().toArray());
	}
	
	public void changeToDead() {
		PacketPlayOutBed bed = new PacketPlayOutBed();
		
		setField(bed, "a", 10000);
		setField(bed, "b", (int) loc.getX());
		setField(bed, "c", (int) loc.getY());
		setField(bed, "d", (int) loc.getZ());
		
		PacketPlayOutEntityTeleport tp = new PacketPlayOutEntityTeleport();
		
		setField(tp, "a", 10000);
		setField(tp, "b", toFixedPoint(loc.getX()));
		setField(tp, "c", toFixedPoint(loc.getY() - 0.34));
		setField(tp, "d", toFixedPoint(loc.getZ()));
		setField(tp, "e", toPackedByte(0f));
		setField(tp, "f", toPackedByte(0f));
		
		sendPacket(bed, (Player[]) PlayerManager.getAll().toArray());
		sendPacket(tp, (Player[]) PlayerManager.getAll().toArray());
	}
	
	private void sendPacket(Packet packet, Player... allPlayer) {
		for (Player p : allPlayer)
			((CraftPlayer) p).getHandle().playerConnection.sendPacket(packet);
	}

	private void setField(Packet packet, String name, Object value) {
		try {
			Field field = packet.getClass().getDeclaredField(name);
			field.setAccessible(true);
			field.set(packet, value);
		} catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException e) {
			e.printStackTrace();
		}
	}
	
	private int toFixedPoint(double d) {
		return (int)d * 32;
	}
	
	private byte toPackedByte(float f) {
		return (byte) ((int) (f * 256.0F / 360.0F));
	}
	
}
