package me.mani.panemgames;

import me.mani.panemgames.commands.HologramCommand;
import me.mani.panemgames.commands.PingCommand;
import me.mani.panemgames.commands.RemovePointCommand;
import me.mani.panemgames.commands.SetPointCommand;
import me.mani.panemgames.holograms.Hologram;
import me.mani.panemgames.listener.PlayerJoinListener;

import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scoreboard.DisplaySlot;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketEvent;

public class PanemGames extends JavaPlugin implements Listener {

	private PlayerScoreboardManager playerScoreboardManager;
	private TimeManager timeManager;
	
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

		startPacketListening();
		
		// Load Locations
		
		LocationManager.loadAll();
		
		// Scoreboard
		
		playerScoreboardManager = new PlayerScoreboardManager("§e- PanemGames -", DisplaySlot.SIDEBAR);
		
		playerScoreboardManager.addValueAll("§7Zeit:", 5);
		playerScoreboardManager.addValueAll("§1", 3);
		playerScoreboardManager.addValueAll("§7Temperatur:", 2);
		
		// TimeManager
		
		timeManager = new TimeManager(this, Bukkit.getWorld("world"));
		
		// UpdatingManager
		
		UpdatingScheduler updatingScheduler = new UpdatingScheduler();
		UpdatingScheduler.add(timeManager);
		updatingScheduler.startUpdatingSchedule(this);	
		
		// Hologram
		
		welcomeHologram = new Hologram("welcomeHologram", LocationManager.getLocation("lobbyWelcome").getLocation());
		welcomeHologram.addLine("§7Willkommen bei §ePanemGames");
		
		// Starte Lobbyphase
		
		PlayerManager.sendAll("§7[§ePanemGames§7] §8Willkommen bei §cPanemGames");
		CountdownManager cm = new CountdownManager(this);
		Lobby lobby = new Lobby(cm);
		
		Bukkit.getPluginManager().registerEvents(lobby, this);
		
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
	
	public void startPacketListening() {
		ProtocolLibrary.getProtocolManager().addPacketListener(new PacketAdapter(this, PacketType.Play.Server.SPAWN_ENTITY_LIVING) {
			
			@Override
			public void onPacketSending(PacketEvent ev) {
				
			}
			
		});
	}
}
