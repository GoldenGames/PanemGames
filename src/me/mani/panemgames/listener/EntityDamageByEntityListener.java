package me.mani.panemgames.listener;

import me.mani.panemgames.AttackCause;
import me.mani.panemgames.DamageCalculater;
import me.mani.panemgames.ItemManager;
import me.mani.panemgames.PanemGames;

import org.bukkit.Material;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Damageable;
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
		Damageable e = (Damageable) ev.getEntity();
		int baseDamage = 0;
		if (ev.getDamager() instanceof Arrow)
			baseDamage = ItemManager.getBaseDamage(Material.ARROW);
		else {
			if (ev.getDamager() instanceof Player) {	
				Player p = (Player) ev.getDamager();
				baseDamage = ItemManager.getBaseDamage(p.getItemInHand().getType());
			}
		}
		DamageCalculater damageCalc = new DamageCalculater(baseDamage);
		AttackCause cause;
		if (ev.getDamager() instanceof Player)
			cause = AttackCause.PLAYER;
		else 
			cause = AttackCause.ENTITY;
		damageCalc.damage(e, cause);
		ev.setDamage(0.0);
	}
}
