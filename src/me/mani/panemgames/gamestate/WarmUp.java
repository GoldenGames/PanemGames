package me.mani.panemgames.gamestate;

import java.util.Random;

import me.mani.panemgames.CountdownCallback;
import me.mani.panemgames.CountdownManager;
import me.mani.panemgames.CountdownManager.Countdown;
import me.mani.panemgames.LocationManager;
import me.mani.panemgames.PanemPlayer;
import me.mani.panemgames.PlayerManager;
import me.mani.panemgames.effects.ParticleEffect;
import me.mani.panemgames.event.PanemPlayerDeathEvent;
import me.mani.panemgames.event.countdown.CountdownCountEvent;
import me.mani.panemgames.gamestate.GameStateManager.GameState;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.util.Vector;

public class WarmUp extends GameStateComponent {

	private Countdown warmUpCountdown;
	
	public WarmUp() {
		super(GameState.WARM_UP);
	}
	
	@Override
	public void start() {
		startWarmUp();
	}
	
	public void startWarmUp() {
		
		// Teleport
		
		int i = 1;
		for (Player p : PlayerManager.getAll()) {
			p.teleport(LocationManager.getSpawnLocations(i).getLocation());
		}
		
		// Countdown - Time
		
		CountdownManager.createCountdown(new CountdownCallback() {
			
			@Override
			public void onCountdownFinish() {
				
			}
			
			@Override
			public void onCountdownCount(CountdownCountEvent ev) {
				if (ev.getCurrentNumber() % 5 != 0)
					return;
				for (int i = 1; i < 24; i++) {
					if (LocationManager.getSpawnLocations(i) == null)
						continue;
					Location loc = LocationManager.getSpawnLocations(i).getLocation();
					loc.getBlock().setType(Material.PISTON_BASE);
					loc.getBlock().setData((byte) 1);
				}
			}
			
		}, 15, 0, 20L);
	}
	
	@EventHandler
	public void onDeath(PanemPlayerDeathEvent ev) {
		PanemPlayer p = ev.getPanemPlayer();
		p.setAlive(false);
		PlayerManager.playAll(Sound.AMBIENCE_THUNDER);
	}
	
	@EventHandler
	public void onMove(PlayerMoveEvent ev) {
		Player p = ev.getPlayer();
		if (ev.getTo().getBlock().getType() == Material.WOOD_PLATE || ev.getTo().getBlock().getType() == Material.STONE_PLATE) {
			ParticleEffect.LARGE_EXPLODE.display(2, 2, 2, 1, 10, ev.getTo(), 20);
			ev.getTo().getBlock().setType(Material.AIR);
			Random random = new Random();
			Vector pushFrom = ev.getTo().toVector();
			Vector pushTo = ev.getTo().getBlock().getLocation().add(0.5, 0.5, 0.5).toVector();
			p.setVelocity(pushFrom.subtract(pushTo).normalize().multiply(random.nextInt(2) + 3));
			p.playSound(p.getLocation(), Sound.EXPLODE, 2, 5);
			p.setHealth(0.0);
		}
	}
}
