package me.mani.panemgames;

import java.util.Arrays;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.scoreboard.Scoreboard;

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

}
