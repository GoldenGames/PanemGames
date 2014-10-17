package me.mani.panemgames.listener;

import me.mani.panemgames.DamageCalculater;
import me.mani.panemgames.ItemManager;
import me.mani.panemgames.PanemGames;
import me.mani.panemgames.PanemPlayer;
import me.mani.panemgames.PlayerManager;
import me.mani.panemgames.event.PanemPlayerDeathEvent;
import me.mani.panemgames.event.PanemPlayerDeathEvent.DeathCause;

import org.bukkit.Bukkit;
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
		double newHealth = e.getHealth() - new DamageCalculater(baseDamage).getTotalDamage();
		if (newHealth < 0)
			newHealth = 0.0;
		e.setHealth(newHealth);
		ev.setDamage(0.0);
		if (e instanceof Player) {
			if (!PanemPlayer.isPanemPlayer((Player) e))
					((Player) e).kickPlayer("§eFehlerhafter Spieler!");
			else
				Bukkit.getPluginManager().callEvent(new PanemPlayerDeathEvent(PanemPlayer.getPanemPlayer((Player) e), DeathCause.TRIBUTE));
		}
	}
}
