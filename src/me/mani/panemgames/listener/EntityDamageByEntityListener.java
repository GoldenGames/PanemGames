package me.mani.panemgames.listener;

import me.mani.panemgames.DamageCalculater;
import me.mani.panemgames.ItemManager;
import me.mani.panemgames.PanemGames;

import org.bukkit.entity.Damageable;
import org.bukkit.entity.Entity;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.LivingEntity;
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
		if (!(ev.getEntity() instanceof Damageable))
			return;
		if (!(ev.getDamager() instanceof Player))
			return;
		Damageable e = (Damageable) ev.getEntity();
		Player p = (Player) ev.getDamager();
		int baseDamage = ItemManager.getBaseDamage(p.getItemInHand().getType());
		double newHealth = e.getHealth() - new DamageCalculater(baseDamage).getTotalDamage();
		if (newHealth < 0)
			newHealth = 0.0;
		e.setHealth(newHealth);
	}

}
