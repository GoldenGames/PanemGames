package me.mani.panemgames.listener;

import me.mani.panemgames.PanemGames;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityShootBowEvent;

public class EntityShootBowListener implements Listener {
	
	private PanemGames pl;

	public EntityShootBowListener(PanemGames pl) {
		this.pl = pl;
	}
	
	@EventHandler
	public void onEntityShootBow(EntityShootBowEvent ev) {
		if (ev.getForce() != 1.0f)
			ev.setCancelled(true);
	}

}
