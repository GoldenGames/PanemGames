package me.mani.panemgames;

import java.util.ArrayList;
import java.util.List;

import me.mani.panemgames.PlayerManager.ChatPosition;
import me.mani.panemgames.TimeManager.Time;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Score;

public class TemperatureManager implements Updatable {
	
	private PanemGames pl;
	
	private double currentTemperature;
	private double currentBaseTemperature;
	private double currentBodyStatus = 0.0;
	private Time tempTime;
	private Player p;
	private Score temperatureScore;
	private Score bodyScore;
	
	public TemperatureManager(PanemGames pl, Player p) {
		this.pl = pl;
		this.p = p;
		
		allManagers.add(this);
	}
	
	public void update() {
		this.currentTemperature = getTemperature();
		updateBody();
		if (temperatureScore == null)
			temperatureScore = PlayerScoreboardManager.getPlayerScoreboard(getPlayer()).addValue("§e" + currentTemperature + "°", 7);
		if (temperatureScore.getEntry().equals("§e" + currentTemperature + "°"))
			return;
		PlayerScoreboardManager.getPlayerScoreboard(getPlayer()).resetScore(temperatureScore);
		temperatureScore = PlayerScoreboardManager.getPlayerScoreboard(getPlayer()).addValue("§e" + currentTemperature + "°", 7);	
	}
	
	private void updateBody() {
		this.currentBodyStatus = getBodyStatus();
		if (currentBodyStatus > 3 || currentBodyStatus < -3)
			PlayerManager.send("§4KRITISCHER ZUSTAND", ChatPosition.ABOVE_ACTION_BAR, getPlayer());
		if (bodyScore == null)
			bodyScore = PlayerScoreboardManager.getPlayerScoreboard(getPlayer()).addValue(buildBodyStatusBar(currentBodyStatus), 1);
		if (bodyScore.getEntry().equals(buildBodyStatusBar(currentBodyStatus)))
			return;
		PlayerScoreboardManager.getPlayerScoreboard(getPlayer()).resetScore(bodyScore);
		bodyScore = PlayerScoreboardManager.getPlayerScoreboard(getPlayer()).addValue(buildBodyStatusBar(currentBodyStatus), 1);
	}
	
	public Player getPlayer() {
		return this.p;
	}
	
	private double getTemperature() {
		double temperature = 0.0;
		
		if (tempTime == null || !tempTime.equals(this.getTime())) {
			tempTime = this.getTime();
			temperature = this.getRandomDouble(tempTime.getMinTemperature(), tempTime.getMaxTemperature());
			currentBaseTemperature = temperature;
		}
		else
			temperature = currentBaseTemperature;

		if (isNextToFire())
			temperature += 10.0;
		if (isInWind())
			temperature -= 5.0;
		if (isInWater())
			temperature -= 10.0;
		if (isInRain())
			temperature -= 5.0;	
		
		temperature = Math.round(10.0 * temperature) / 10.0;

		return temperature;
	}
	
	private Time getTime() {
		return pl.getTimeManager().getCurrentTime();
	}
	
	@SuppressWarnings("deprecation")
	private boolean isNextToFire() {
		if (p.getTargetBlock(null, 2).getType().equals(Material.FIRE) && ItemManager.isCookable(p.getItemInHand().getType()))
			CookManager.startCookTask(p);
		return p.getTargetBlock(null, 2).getType().equals(Material.FIRE);
	}
	
	private double getBodyStatus() {
		double body = currentBodyStatus;
		
		if (currentTemperature <= 10)
			body -= 0.04;
		else if (currentTemperature >= 30)
			body += 0.04;
		else
			body = body > 0 ? body - 0.02 : body + 0.02;
		
		return body;
	}
	
	private String buildBodyStatusBar(double bodyStatus) {
		String bodyStatusBar = "";
		if (bodyStatus < -4)
			bodyStatusBar = "§b██§8████████";
		else if (bodyStatus >= -4 && bodyStatus < -3)
			bodyStatusBar = "§8█§3██§8███████";
		else if (bodyStatus >= -3 && bodyStatus < -2)
			bodyStatusBar = "§8██§9██§8██████";
		else if (bodyStatus >= -2 && bodyStatus < -1)
			bodyStatusBar = "§8███§2██§8█████";
		else if (bodyStatus >= -1 && bodyStatus < 1)
			bodyStatusBar = "§8████§a██§8████";				// Mitte (Perfekt)
		else if (bodyStatus >= 1 && bodyStatus < 2)
			bodyStatusBar = "§8█████§2██§8███";
		else if (bodyStatus >= 2 && bodyStatus < 3)
			bodyStatusBar = "§8██████§5██§8██";
		else if (bodyStatus >= 3 && bodyStatus < 4)
			bodyStatusBar = "§8███████§c██§8█";
		else
			bodyStatusBar = "§8████████§4██";
		return bodyStatusBar;
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
