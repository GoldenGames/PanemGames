package me.mani.panemgames;

import java.util.ArrayList;
import java.util.List;

import me.mani.panemgames.TimeManager.Time;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Score;

public class TemperatureManager implements Updatable {
	
	private PanemGames pl;
	
	private double currentTemperature;	
	private Player p;
	private Score s;
	
	public TemperatureManager(PanemGames pl, Player p) {
		this.pl = pl;
		this.p = p;
		
		allManagers.add(this);
	}
	
	public void update() {
		this.currentTemperature = getTemperature();
		if (s == null)
			s = PlayerScoreboardManager.getPlayerScoreboard(getPlayer()).addValue("§e" + currentTemperature + "°", 1);
		if (s.getEntry().equals("§e" + currentTemperature + "°"))
			return;
		PlayerScoreboardManager.getPlayerScoreboard(getPlayer()).resetScore(s);
		s = PlayerScoreboardManager.getPlayerScoreboard(getPlayer()).addValue("§e" + currentTemperature + "°", 1);
	}
	
	public Player getPlayer() {
		return this.p;
	}
	
	private double getTemperature() {
		Time time = this.getTime();
		double temperature = this.getRandomDouble(time.getMinTemperature(), time.getMaxTemperature());

		if (isNextToFire())
			temperature += 10;
		if (isInWind())
			temperature -= 5;
		if (isInWater())
			temperature -= 10;
		if (isInRain())
			temperature -= 5;
		
		temperature = Math.round(10.0 * temperature) / 10.0;
		
		return temperature;
	}
	
	private Time getTime() {
		return pl.getTimeManager().getCurrentTime();
	}
	
	private boolean isNextToFire() {
		return false;
	}
	
	private boolean isInWind() {
		return false;
	}
	
	private boolean isInWater() {
		Material type = p.getLocation().getBlock().getType();
		if (type == Material.STATIONARY_WATER || type == Material.WATER)
			return true;
		return false;
	}
	
	private boolean isInRain() {
		return p.getWorld().hasStorm();
	}
	
	private double getRandomDouble(int min, int max) {
		return min + (Math.random() * ((max - min) + 1));
	}
	
	// Static
	
	private static List<TemperatureManager> allManagers = new ArrayList<>();
	
	public static TemperatureManager getTemperatureManager(Player p) {
		for (TemperatureManager tm : allManagers) {
			if (tm.getPlayer().equals(p))
				return tm;
		}
		return null;
	}

}
