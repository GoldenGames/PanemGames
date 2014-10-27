package me.mani.panemgames.gamestate;

import java.util.HashMap;
import java.util.Iterator;

import me.mani.panemgames.AnimationManager;
import me.mani.panemgames.CountdownCallback;
import me.mani.panemgames.CountdownManager;
import me.mani.panemgames.LocationManager;
import me.mani.panemgames.PanemPlayer;
import me.mani.panemgames.PlayerManager;
import me.mani.panemgames.PlayerScoreboardManager;
import me.mani.panemgames.event.countdown.CountdownCountEvent;
import me.mani.panemgames.event.player.PanemPlayerDeathEvent;
import me.mani.panemgames.gamestate.GameStateManager.GameState;

import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerMoveEvent;

public class WarmUp extends GameStateComponent {
	
	private Iterator<Player> allPlayer;

	public WarmUp() {
		super(GameState.WARM_UP);
	}
	
	@Override
	public void start() {
		teleportAll();
	}
	
	@Override
	public void setupScoreboard(PlayerScoreboardManager playerScoreboardManager) {
		
	}
	
	private HashMap<Player, Location> allPlayerLocation = new HashMap<>();
	
	public void teleportAll() {
		
		allPlayer = PlayerManager.getAll().iterator();
		
		// Teleportcountdown 
		
		CountdownManager.createCountdown(new CountdownCallback() {
			
			@Override
			public void onCountdownFinish() {
				startWarmUp();
			}
			
			@Override
			public void onCountdownCount(CountdownCountEvent ev) {
				if (LocationManager.getSpawnLocations(ev.getCurrentNumber()) == null) {
					cancelCountdown();
					return;
				}
				if (allPlayer.hasNext()) {
					Player p = allPlayer.next();
					Location loc = LocationManager.getSpawnLocations(ev.getCurrentNumber()).getLocation();
					p.teleport(loc);
					allPlayerLocation.put(p, loc);
				}
				else
					cancelCountdown();
			}
			
		}, 1, 24, 5L);
	}
	
	public void startWarmUp() {
		
		// Teleport Fix
		
		PlayerManager.setInvisibleAll(true);
		PlayerManager.setInvisibleAll(false);
				
		// Countdown
		
		CountdownManager.createCountdown(new CountdownCallback() {

			@Override
			public void onCountdownFinish() {
				finish(new Ingame());
			}
			
			@Override
			public void onCountdownCount(CountdownCountEvent ev) {
				PlayerManager.sendAll(" \n \n \n" + "§7[§ePanemGames§7] §8Noch §b" + ev.getCurrentNumber() + " §8Sekunden bis zum Start!" + "\n \n \n ");
				for (Player p : PlayerManager.getAll()) {
						AnimationManager.playAnimationCircle(p.getLocation().getBlock());
						AnimationManager.playAnimationCircle(p.getLocation().clone().add(0, 1, 0).getBlock());
				}
			}
			
		}, 10, 0, 20L);
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
		
		if (ev.getTo().getX() != allPlayerLocation.get(p).getX() || ev.getTo().getZ() != allPlayerLocation.get(p).getZ()) {
			p.teleport(new Location(ev.getTo().getWorld(), allPlayerLocation.get(p).getX(), ev.getTo().getY(), allPlayerLocation.get(p).getZ(), ev.getTo().getYaw(), ev.getTo().getPitch()));
		}
	}
}
