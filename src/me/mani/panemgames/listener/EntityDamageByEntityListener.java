package me.mani.panemgames.listener;

import me.mani.panemgames.ItemManager;
import me.mani.panemgames.PanemGames;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class EntityDamageByEntityListener implements Listener {
	
	private PanemGames pl;

	public EntityDamageByEntityListener(PanemGames pl) {
		this.pl = pl;
	}
	
	@EventHandler
	public void onEntityDamageByEntity(EntityDamageByEntityEvent ev) {
		if (!(ev.getDamager() instanceof Player))
			return;
		Player p = (Player) ev.getDamager();
		if (ItemManager.getByItemStack(p.getItemInHand()) != null) {
			ev.setDamage((double) ItemManager.getByItemStack(p.getItemInHand()).getDamage());
		}
	}

}
