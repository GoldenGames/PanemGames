package me.mani.panemgames;

import java.util.Arrays;
import java.util.List;

import net.minecraft.server.v1_7_R4.ChatSerializer;
import net.minecraft.server.v1_7_R4.EntityPlayer;
import net.minecraft.server.v1_7_R4.EnumClientCommand;
import net.minecraft.server.v1_7_R4.EnumGamemode;
import net.minecraft.server.v1_7_R4.IChatBaseComponent;
import net.minecraft.server.v1_7_R4.PacketPlayInClientCommand;
import net.minecraft.server.v1_7_R4.PacketPlayOutChat;
import net.minecraft.server.v1_7_R4.PacketPlayOutGameStateChange;
import net.minecraft.server.v1_7_R4.PacketSplitter;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.craftbukkit.v1_7_R4.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.scoreboard.Scoreboard;
import org.spigotmc.ProtocolInjector.PacketPlayResourcePackStatus;

@SuppressWarnings("deprecation")
public class PlayerManager {
	
	public static String pre = "§7[§ePanemGames§7]";
	
	public static void sendAll(String message) {
		Bukkit.broadcastMessage(message);
	}
	
	public static void send(String message, Player p) {
		p.sendMessage(message);
	}
	
	public static void send(String message, Player... player) {
		for (Player p : player) {
			p.sendMessage(message);
		}
	}
	
	public static void send(String message, ChatPosition pos, Player p) {
		IChatBaseComponent text = ChatSerializer.a("{text:\"" + message + "\"}");
		PacketPlayOutChat packet = new PacketPlayOutChat(text, pos.getID());
		if (pos.equals(ChatPosition.ABOVE_ACTION_BAR) &&
				((CraftPlayer) p).getHandle().playerConnection.networkManager.getVersion() < 47)
			return;
		((CraftPlayer) p).getHandle().playerConnection.sendPacket(packet);
	}
	
	public static void setSpectatorMode(Player p) {
		if (((CraftPlayer) p).getHandle().playerConnection.networkManager.getVersion() < 47)
			return;
		((CraftPlayer) p).getHandle().playerConnection.sendPacket(new PacketPlayOutGameStateChange(3, 3f));
	}

	public static void playAll(Sound sound) {
		for (Player p : Bukkit.getOnlinePlayers()) {
			p.playSound(p.getLocation(), sound, 1f, 1f);
		}
	}
	
	public static void teleportAll(Location loc) {
		for (Player p : Bukkit.getOnlinePlayers()) {
			p.teleport(loc);
		}
	}
	
	public static int getPing(Player p) {
		CraftPlayer cp = (CraftPlayer) p;
		EntityPlayer ep = cp.getHandle();
		return ep.ping;
	}
	
	public static void teleportAll(Location... loc) {
		int i = 0;
		for (Player p : Bukkit.getOnlinePlayers()) {
			p.teleport(loc[i]);
			if (i == loc.length - 1) {
				i = 0;
				continue;
			}
			i++;
		}
	}

	public static void setInventoryAll(Inventory inv) {
		for (Player p : Bukkit.getOnlinePlayers()) {
			p.getInventory().clear();
			for (int i = 0; i < 35; i++) {
				if (inv.getItem(i) == null)
					continue;
				p.getInventory().setItem(i, inv.getItem(i));
			}
		}
	}
	
	public static void setScoreboardAll(Scoreboard s) {
		for (Player p : Bukkit.getOnlinePlayers()) {
			p.setScoreboard(s);
		}
	}

	public static List<Player> getAll() {
		return Arrays.asList(Bukkit.getOnlinePlayers());
	}
	
	public static enum ChatPosition {
		CHAT_BOX (0),
		CHAT_BOX_SYSTEM (1),
		ABOVE_ACTION_BAR (2);
		
		private ChatPosition(int i) {
			this.i = i;
		}
		
		private int i;
		
		public int getID() {
			return this.i;
		}
	}

}
