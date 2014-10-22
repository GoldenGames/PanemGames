package me.mani.panemgames;

import me.mani.panemgames.CountdownManager.Countdown;
import me.mani.panemgames.event.PanemPlayerDeathEvent;
import me.mani.panemgames.event.countdown.CountdownCountEvent;

import org.bukkit.Bukkit;
import org.bukkit.entity.Damageable;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;


public class DamageCalculater {

	private double totalDamage = 0.0;
	
	public DamageCalculater(int baseDamage) {
		totalDamage = (double) baseDamage;
	}
	
	public DamageCalculater(int baseDamage, DamageModifier... damageModifier) {
		this((double) baseDamage, damageModifier);
	}
	
	public DamageCalculater(double baseDamage, DamageModifier... damageModifier) {
		totalDamage = baseDamage;
		for (DamageModifier modifier : damageModifier)
			totalDamage = totalDamage*(1.0 + (modifier.getPower() / 100));	
	}
	
	public double getTotalDamage() {
		return totalDamage;
	}
	
	public boolean damage(Damageable e, AttackCause cause) {
		double newHealth = e.getHealth() - getTotalDamage();
		boolean dead = false;
		if (newHealth <= 0) {
			if (e instanceof Player)
				Bukkit.getPluginManager().callEvent(new PanemPlayerDeathEvent(PanemPlayer.getPanemPlayer((Player) e), cause));
			newHealth = 0.0;
			dead = true;
		}
		e.setHealth(newHealth);
		e.damage(0.0);
		if (e instanceof Player)
			PlayerManager.updateBloodyScreen((Player) e);
		return dead;
	}
	
	public TimerDamage multiDamage(final Damageable e, final AttackCause cause, int delayInSeconds) {
		return multiDamage(e, cause, delayInSeconds, -1);
	}
	
	public TimerDamage multiDamage(final Damageable e, final AttackCause cause, int delayInSeconds, int times) {
		TimerDamage timerDamage = new TimerDamage(e, cause, getTotalDamage(), times);
		timerDamage.runTaskTimer(PanemGames.getPanemGames(), 0L, delayInSeconds*20);
		return timerDamage;
	}
	
	public abstract class DamageModifier {
		
		private DamageModifierType type;
		private double power;
		
		public DamageModifier(DamageModifierType type, double power) {
			this.type = type;
			this.power = power;
		}
		
		public DamageModifierType getType() {
			return type;
		}
		
		public double getPower() {
			return power;
		}	
	}
	
	public class TimerDamage extends BukkitRunnable {

		public TimerDamage(Damageable e,  AttackCause damageCause, double damage) {
			this(e, damageCause, damage, -1);
		}
		
		public TimerDamage(Damageable e, AttackCause damageCause, double damage, int times) {
			this.e = e;
			this.damageCause = damageCause;
			this.damage = damage;
			this.times = times;
		}
		
		private Damageable e;
		private AttackCause damageCause;
		private double damage;
		private int times;
		
		@Override
		public void run() {	
			if(new DamageCalculater(damage).damage(e, damageCause))
				stop();
			if (times == -1)
				return;
			times--;
			if (times > 0)
				return;
			stop();
		}
		
		public void stop() {
			this.cancel();
		}	
	}
	
	public enum DamageModifierType {
		ATTACKER_ADVANTAGE,
		ATTACKED_ADVANTAGE,
		ATTACKER_DISADVANTAGE,
		ATTACKED_DISADVANTAGE,
		NATURE,
		SPECIAL;
	}
}
