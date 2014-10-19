package me.mani.panemgames.util;

import java.io.IOException;

import net.minecraft.server.v1_7_R4.Packet;
import net.minecraft.server.v1_7_R4.PacketDataSerializer;
import net.minecraft.server.v1_7_R4.PacketListener;

public class PacketCamera extends Packet {
	
	private int entityID;
	
	public PacketCamera(int entityID) {
		this.entityID = entityID;
	}

	@Override
	public void a(PacketDataSerializer packetDataSerializer) throws IOException {
		this.entityID = packetDataSerializer.a();
	}

	@Override
	public void b(PacketDataSerializer packetDataSerializer) throws IOException {
		packetDataSerializer.b(this.entityID);
	}

	@Override
	public void handle(PacketListener packetListener) {}
	
}
