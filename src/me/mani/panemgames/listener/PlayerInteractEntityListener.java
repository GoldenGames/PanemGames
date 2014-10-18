package me.mani.panemgames.listener;

import me.mani.panemgames.PanemGames;
import me.mani.panemgames.PanemPlayer;
import me.mani.panemgames.PlayerManager;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;

public class PlayerInteractEntityListener implements Listener {
	
	private PanemGames pl;
	
	public PlayerInteractEntityListener(PanemGames pl) {
		this.pl = pl;
	}
	
	@EventHandler
	public void onEntityInteract(PlayerInteractEntityEvent ev) {
		Player p = ev.getPlayer();
		Entity clicked = ev.getRightClicked();
		if (!PanemPlayer.isPanemPlayer(p))
			return;
		PanemPlayer.getPanemPlayer(p).createSpectate(clicked);
	}

}
