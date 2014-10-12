package me.mani.panemgames.gamestate;

import me.mani.panemgames.CountdownCallback;
import me.mani.panemgames.CountdownManager;
import me.mani.panemgames.CountdownManager.Countdown;
import me.mani.panemgames.CountdownManager.Countdown.CountdownCountEvent;
import me.mani.panemgames.LocationManager;
import me.mani.panemgames.PanemGames;
import me.mani.panemgames.PlayerManager;

import org.bukkit.entity.Player;

public class WarpUp {

	CountdownManager countdownManager;
	Countdown warpUpCountdown;
	
	public WarpUp(PanemGames pl) {
		countdownManager = pl.getCountdownManager();
	}
	
	public void startWarpUp() {
		
		// Teleport
		
		int i = 1;
		for (Player p : PlayerManager.getAll()) {
			p.teleport(LocationManager.getSpawnLocations(i).getLocation());
		}
		
		// Countdown
		
		countdownManager.createCountdown(new CountdownCallback() {
			
			@Override
			public void onCountdownFinish() {
				
			}
			
			@Override
			public void onCountdownCount(CountdownCountEvent ev) {
				// TODO Auto-generated method stub
				
			}
		}, 15, 0);
	}
}
