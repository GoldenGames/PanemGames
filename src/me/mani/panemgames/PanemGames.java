package me.mani.panemgames;

import me.mani.panemgames.commands.HologramCommand;
import me.mani.panemgames.commands.PingCommand;
import me.mani.panemgames.commands.RemovePointCommand;
import me.mani.panemgames.commands.SetPointCommand;
import me.mani.panemgames.gamestate.Lobby;
import me.mani.panemgames.holograms.Hologram;
import me.mani.panemgames.listener.PlayerJoinListener;

import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scoreboard.DisplaySlot;

public class PanemGames extends JavaPlugin implements Listener {

	private PlayerScoreboardManager playerScoreboardManager;
	private TimeManager timeManager;
	private CountdownManager countdownManager;
	
	private Hologram welcomeHologram;
	
	@Override
	public void onEnable() {
				
		// Commands
		
		this.getCommand("setpoint").setExecutor(new SetPointCommand());
		this.getCommand("removepoint").setExecutor(new RemovePointCommand());
		this.getCommand("ping").setExecutor(new PingCommand());
		this.getCommand("hologram").setExecutor(new HologramCommand());
		
		// Listener
		
		Bukkit.getPluginManager().registerEvents(new PlayerJoinListener(this), this);
		
		// Load Locations
		
		LocationManager.loadAll();
		
		// Scoreboard
		
		playerScoreboardManager = new PlayerScoreboardManager("§e- PanemGames -", DisplaySlot.SIDEBAR);
		
		playerScoreboardManager.addValueAll("§7Zeit:", 11);			//	11
																	//	10
		playerScoreboardManager.addValueAll("§1", 9);				//	9
		playerScoreboardManager.addValueAll("§7Temperatur:", 8);	//	8
																	//	7
		playerScoreboardManager.addValueAll("§2", 6);				//	6
		playerScoreboardManager.addValueAll("§7Durst/Hunger:", 5);	//	5
		playerScoreboardManager.addValueAll("§6██████████", 4);	//	4
		playerScoreboardManager.addValueAll("§3", 3);				//	3
		playerScoreboardManager.addValueAll("§7Körper:", 2);		//	2
		playerScoreboardManager.addValueAll("§8███§a██§8█████", 1);	//	1
		
		// TimeManager
		
		timeManager = new TimeManager(this, Bukkit.getWorld("world"));
		
		// CountdownManager
		
		countdownManager = new CountdownManager(this);
		
		// UpdatingManager
		
		UpdatingScheduler updatingScheduler = new UpdatingScheduler();
		UpdatingScheduler.add(timeManager);
		updatingScheduler.startUpdatingSchedule(this);	
		
		// Hologram
		
		welcomeHologram = new Hologram("welcomeHologram", LocationManager.getLocation("lobbyWelcome").getLocation());
		welcomeHologram.addLine("§7Willkommen bei §ePanemGames");
		
		// Starte Lobbyphase
		
		PlayerManager.sendAll("§7[§ePanemGames§7] §8Willkommen bei §cPanemGames");
		Lobby lobby = new Lobby(this);
		
		Bukkit.getPluginManager().registerEvents(lobby, this); // Lobbylistener
		
		// NUR ZUM TESTEN TODO: ENTFERNEN
		
		lobby.startCountdown();
	}
	
	@Override
	public void onDisable() {
		
		// Save Locations
		
		LocationManager.saveAll();
		
		//Remove Hologram(s)
		
		if (welcomeHologram != null)
			welcomeHologram.remove();
		
	}

	public PlayerScoreboardManager getPlayerScoreboardManager() {
		return this.playerScoreboardManager;
	}
	
	public TimeManager getTimeManager() {
		return this.timeManager;
	}
	
	public CountdownManager getCountdownManager() {
		return this.countdownManager;
	}
}
