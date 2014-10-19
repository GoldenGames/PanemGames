package me.mani.panemgames.util;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import net.minecraft.server.v1_7_R4.EnumProtocol;
import net.minecraft.server.v1_7_R4.Packet;

import org.spigotmc.ProtocolInjector;

public class PacketManager {
	
	public static void registerPacket(EnumProtocol protocol, boolean serverbound, int packetId, Class<? extends Packet> packetClass) {
		try {
			Method method = ProtocolInjector.class.getDeclaredMethods()[1];
			method.setAccessible(true);
			method.invoke(ProtocolInjector.class, protocol, serverbound, packetId, packetClass);
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException | SecurityException e) {
			e.printStackTrace();
		}
	}

}
