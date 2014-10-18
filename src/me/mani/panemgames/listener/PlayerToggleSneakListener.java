package me.mani.panemgames.listener;

import me.mani.panemgames.PanemGames;
import me.mani.panemgames.PanemPlayer;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerToggleSneakEvent;

public class PlayerToggleSneakListener implements Listener {
	
	private PanemGames pl;
	
	public PlayerToggleSneakListener(PanemGames pl) {
		this.pl = pl;
	}
	
	@EventHandler
	public void onToggleSneak(PlayerToggleSneakEvent ev) {
		Player p = ev.getPlayer();
		if (!PanemPlayer.isPanemPlayer(p))
			p.kickPlayer("§eFehlerhafter Spieler");
		else {
			if (PanemPlayer.getPanemPlayer(p).isSpectating())
				PanemPlayer.getPanemPlayer(p).removeSpectate();
		}
	}

}
