package me.mani.panemgames;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import me.mani.panemgames.commands.HologramCommand;
import me.mani.panemgames.commands.PingCommand;
import me.mani.panemgames.commands.RemovePointCommand;
import me.mani.panemgames.commands.SetPointCommand;
import me.mani.panemgames.config.ConfigManager;
import me.mani.panemgames.config.ItemFile;
import me.mani.panemgames.gamestate.GameStateManager;
import me.mani.panemgames.gamestate.Lobby;
import me.mani.panemgames.holograms.Hologram;
import me.mani.panemgames.listener.EntityDamageByEntityListener;
import me.mani.panemgames.listener.PlayerJoinListener;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scoreboard.DisplaySlot;

public class PanemGames extends JavaPlugin implements Listener {

	private static PanemGames instance;
	
	private PlayerScoreboardManager playerScoreboardManager;
	private TimeManager timeManager;
	private GameStateManager gameStateManager;
	private ConfigManager configManager;
	
	private Hologram welcomeHologram;
	
	@Override
	public void onEnable() {
				
		// Instance
		
		instance = this;
		
		// Commands
		
		this.getCommand("setpoint").setExecutor(new SetPointCommand());
		this.getCommand("removepoint").setExecutor(new RemovePointCommand());
		this.getCommand("ping").setExecutor(new PingCommand());
		this.getCommand("hologram").setExecutor(new HologramCommand());
		
		// Listener
		
		Bukkit.getPluginManager().registerEvents(new PlayerJoinListener(this), this);
		Bukkit.getPluginManager().registerEvents(new EntityDamageByEntityListener(this), this);
		
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
		
		// UpdatingManager
		
		UpdatingScheduler updatingScheduler = new UpdatingScheduler();
		UpdatingScheduler.add(timeManager);
		updatingScheduler.startUpdatingSchedule(this);	
		
		// ConfigManager
		
		configManager = new ConfigManager(new ItemFile("items"));
		configManager.loadAll();
		
		List<String> lore = Arrays.asList("§3Line");

		ItemManager.addItem(new ItemObject(0, Material.IRON_SPADE, "§6Spade", lore, 20));
		
		// Hologram
		
		welcomeHologram = new Hologram("welcomeHologram", LocationManager.getLocation("lobbyWelcome").getLocation());
		welcomeHologram.addLine("§7Willkommen bei §ePanemGames");
		
		// Starte Lobbyphase
		
		PlayerManager.sendAll("§7[§ePanemGames§7] §8Willkommen bei §cPanemGames");
		gameStateManager = new GameStateManager(this, new Lobby(this));
	}
	
	@Override
	public void onDisable() {
		
		// ConfigManager
		
		configManager.saveAll();
		
		// Save Locations
		
		LocationManager.saveAll();
		
		//Remove Hologram(s)
		
		if (welcomeHologram != null)
			welcomeHologram.remove();
		
	}
	
	public static PanemGames getPanemGames() {
		return instance;
	}

	public PlayerScoreboardManager getPlayerScoreboardManager() {
		return this.playerScoreboardManager;
	}
	
	public TimeManager getTimeManager() {
		return this.timeManager;
	}
	
	public GameStateManager getGameStateManager() {
		return this.gameStateManager;
	}
	
}
