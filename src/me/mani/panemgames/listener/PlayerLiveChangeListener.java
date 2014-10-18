package me.mani.panemgames.listener;

import me.mani.panemgames.PanemGames;
import me.mani.panemgames.PlayerManager;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityRegainHealthEvent;

public class PlayerLiveChangeListener implements Listener {
	
	private PanemGames pl;
	
	public PlayerLiveChangeListener(PanemGames pl) {
		this.pl = pl;
	}

	@EventHandler
	public void onDamage(EntityDamageEvent ev) {
		if (!(ev.getEntity() instanceof Player))
			return;
		Player p = (Player) ev.getEntity();
		PlayerManager.updateBloodyScreen(p);
	}
	
	@EventHandler
	public void onHeal(EntityRegainHealthEvent ev) {
		if (!(ev.getEntity() instanceof Player))
			return;
		Player p = (Player) ev.getEntity();
		PlayerManager.updateBloodyScreen(p);
	}
	
}
