package me.mani.panemgames.gamestate;

import me.mani.panemgames.CountdownCallback;
import me.mani.panemgames.CountdownManager;
import me.mani.panemgames.CountdownManager.Countdown;
import me.mani.panemgames.CountdownManager.Countdown.CountdownCountEvent;
import me.mani.panemgames.LocationManager;
import me.mani.panemgames.PanemGames;
import me.mani.panemgames.PlayerManager;
import me.mani.panemgames.gamestate.GameStateManager.GameState;
import me.mani.panemgames.gamestate.GameStateManager.GameStateComponent;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;

public class WarmUp extends GameStateComponent {

	Countdown warpUpCountdown;
	
	public WarmUp(PanemGames pl) {
		super(GameState.WARM_UP);
	}
	
	@Override
	public void start() {
		startWarpUp();
	}
	
	public void startWarpUp() {
		
		// Teleport
		
		int i = 1;
		for (Player p : PlayerManager.getAll()) {
			p.teleport(LocationManager.getSpawnLocations(i).getLocation());
		}
		
		// Countdown - Time
		
		CountdownManager.createCountdown(new CountdownCallback() {
			
			int i = 1;
			
			@Override
			public void onCountdownFinish() {
				
			}
			
			@Override
			public void onCountdownCount(CountdownCountEvent ev) {
				
			}
			
		}, 15, 0, 20L);
		
		// Countdown - Elevator
		
		CountdownManager.createCountdown(new CountdownCallback() {
			
			Location lastLocation;
			
			@Override
			public void onCountdownFinish() {
				
			}
			
			@Override
			public void onCountdownCount(CountdownCountEvent ev) {
				for (Player p : PlayerManager.getAll()) {
					lastLocation = p.getLocation().clone();
					p.setVelocity(p.getLocation().clone().add(0, 0.125, 0).subtract(lastLocation).toVector().normalize());
				}
			}
			
		}, 1, 24, 5L);
	}
}
