package me.mani.panemgames.listener;

import me.mani.panemgames.PanemGames;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

public class PlayerDeathListener implements Listener {
	
	private PanemGames pl;
	
	public PlayerDeathListener(PanemGames pl) {
		this.pl = pl;
	}
	
	@EventHandler
	public void onDeath(PlayerDeathEvent ev) {
		ev.setDeathMessage("");
	}

}
