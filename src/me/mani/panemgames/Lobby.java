package me.mani.panemgames;

import me.mani.panemgames.CountdownManager.Countdown;

import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.EnderCrystal;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;

public class Lobby implements Listener {
	
	private CountdownManager countdownManager;	
	private Countdown lobbyCountdown;

	public Lobby(CountdownManager countdownManager) {
		this.countdownManager = countdownManager;
	}
	
	public void start() {
		
	}
	
	public void startCountdown() {
		lobbyCountdown = countdownManager.runCountdown(new CountdownCallback() {
			
			@Override
			public void onCountdownFinish() {
				startLobbyMinigame();
			}
			
		}, 20, 0, Sound.ORB_PICKUP, "§7[§ePanemGames§7] §8In §e[time] §8Sekunden könnt ihr die Sponsoren beeindrucken!");
	}
	
	public void stopCountdown() {
		lobbyCountdown.forceStop();
	}
	
	public void startLobbyMinigame() {
		PlayerManager.sendAll("§7[§ePanemGames§7] §8Sammle so viele §eOrbs §8wie möglich auf!");
		spawnExp();
		countdownManager.runCountdown(new CountdownCallback() {
			
			@Override
			public void onCountdownFinish() {
				start();
			}
			
		}, 20, 0, Sound.NOTE_BASS, "§7[§ePanemGames§7] §8Das Spiel startet in §e[time] §8Sekunden");
	}
	
	public void spawnExp() {
		Location centerLocation = LocationManager.getLocation("lobbyExp").getLocation();
		for (int x = -10; x < 10; x+=2) {
			for (int z = -10; z < 10; z+=2) {
				Location loc = centerLocation.clone();
				loc.add(x, 0, z);
				EnderCrystal crystal = (EnderCrystal) loc.getWorld().spawnEntity(loc, EntityType.ENDER_CRYSTAL);
			}
		}
	}
	
	/*
	 * @args amount The amount of exp the Player will get (100 means Lvl-Up)
	 */
	public void addExp(int amount, Player p) {
		p.setExp(p.getExp() + (float) amount/100);
		if (p.getExp() >= 1) {
			p.setLevel(p.getLevel() + 1);
			p.setExp(p.getExp() - 1f);
		}
	}
	
	@EventHandler
	public void onEntityInteract(PlayerInteractEntityEvent ev) {
		Player p = ev.getPlayer();
		if (ev.getRightClicked() instanceof EnderCrystal) {
			EnderCrystal crystal = (EnderCrystal) ev.getRightClicked();
			addExp(10, p);
			crystal.remove();
		}
	}
	
	@EventHandler
	public void onEntityAttack(EntityDamageByEntityEvent ev) {
		if (ev.getEntity() instanceof EnderCrystal) {
			EnderCrystal crystal = (EnderCrystal) ev.getEntity();
			if (ev.getDamager() instanceof Player)
				addExp(10, (Player) ev.getDamager());
			ev.setCancelled(true);
			crystal.remove();
		}
	}

}
