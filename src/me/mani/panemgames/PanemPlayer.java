package me.mani.panemgames;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.entity.Player;

public class PanemPlayer {
	
	private static List<PanemPlayer> allPanemPlayers = new ArrayList<>();
	
	private Player bukkitPlayer;
	private boolean isAlive;
	private Tribute tribute;
	
	public PanemPlayer(Player p, Tribute tribute) {
		this(p, tribute, true);
	}
	
	public PanemPlayer(Player p, Tribute tribute, boolean isAlive) {
		this.bukkitPlayer = p;
		this.tribute = tribute;
		this.isAlive = isAlive;
		
		allPanemPlayers.add(this);
	}
	
	public Player getPlayer() {
		return this.bukkitPlayer;
	}
	
	public boolean isAlive() {
		return this.isAlive;
	}
	
	public void setAlive(boolean isAlive) {
		this.isAlive = isAlive;
	}
	
	public Tribute getTribute() {
		return this.tribute;
	}
	
	public void setTribute(Tribute tribute) {
		this.tribute = tribute;
	}
	
	public void destroy() {
		allPanemPlayers.remove(this);
	}
	
	public static boolean isPanemPlayer(Player p) {
		return getPanemPlayer(p) != null;
	}
	
	public static PanemPlayer getPanemPlayer(Player p) {
		for (PanemPlayer pp : allPanemPlayers) {
			if (pp.getPlayer().equals(p))
				return pp;
		}
		return null;
	}	
}
