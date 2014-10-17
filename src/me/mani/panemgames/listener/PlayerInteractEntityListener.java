package me.mani.panemgames.listener;

import me.mani.panemgames.PanemGames;

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
		if (!(ev.getRightClicked() instanceof Player))
			return;
		Player p = ev.getPlayer();
		Player clicked = (Player) ev.getRightClicked();
	}

}
