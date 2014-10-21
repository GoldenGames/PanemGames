package me.mani.panemgames.util;

import me.mani.panemgames.PlayerManager;
import net.minecraft.server.v1_7_R4.DataWatcher;
import net.minecraft.server.v1_7_R4.PacketPlayOutBed;
import net.minecraft.server.v1_7_R4.PacketPlayOutEntityTeleport;
import net.minecraft.server.v1_7_R4.PacketPlayOutNamedEntitySpawn;
import net.minecraft.util.com.mojang.authlib.GameProfile;

import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

public class FakePlayer {
	
	private Location loc;
	private PacketPlayOutNamedEntitySpawn fakePlayer;
	
	public FakePlayer(OfflinePlayer p, Location loc) {
		this.loc = loc;
		
		fakePlayer = new PacketPlayOutNamedEntitySpawn();
		DataWatcher data = new DataWatcher(null);
		data.a(6, (float) 20);
		
		PacketUtil.setField(fakePlayer, "a", 12000);
		PacketUtil.setField(fakePlayer, "b", new GameProfile(p.getUniqueId(), p.getName()));
		PacketUtil.setField(fakePlayer, "c", PacketUtil.toFixedPoint(loc.getX()));
		PacketUtil.setField(fakePlayer, "d", PacketUtil.toFixedPoint(loc.getY()));
		PacketUtil.setField(fakePlayer, "e", PacketUtil.toFixedPoint(loc.getZ()));
		PacketUtil.setField(fakePlayer, "f", PacketUtil.toPackedByte(0f));
		PacketUtil.setField(fakePlayer, "g", PacketUtil.toPackedByte(0f));
		PacketUtil.setField(fakePlayer, "h", 0);
		PacketUtil.setField(fakePlayer, "i", data);
	}	
	
	public void spawn() {
		PacketUtil.sendPacket(fakePlayer, (Player[]) PlayerManager.getAll().toArray());
	}
	
	public void changeToDead() {
		PacketPlayOutBed bed = new PacketPlayOutBed();
		
		PacketUtil.setField(bed, "a", 10000);
		PacketUtil.setField(bed, "b", (int) loc.getX());
		PacketUtil.setField(bed, "c", (int) loc.getY());
		PacketUtil.setField(bed, "d", (int) loc.getZ());
		
		PacketPlayOutEntityTeleport tp = new PacketPlayOutEntityTeleport();
		
		PacketUtil.setField(tp, "a", 10000);
		PacketUtil.setField(tp, "b", PacketUtil.toFixedPoint(loc.getX()));
		PacketUtil.setField(tp, "c", PacketUtil.toFixedPoint(loc.getY() - 0.34));
		PacketUtil.setField(tp, "d", PacketUtil.toFixedPoint(loc.getZ()));
		PacketUtil.setField(tp, "e", PacketUtil.toPackedByte(0f));
		PacketUtil.setField(tp, "f", PacketUtil.toPackedByte(0f));
		
		PacketUtil.sendPacket(bed, (Player[]) PlayerManager.getAll().toArray());
		PacketUtil.sendPacket(tp, (Player[]) PlayerManager.getAll().toArray());
	}	
}
