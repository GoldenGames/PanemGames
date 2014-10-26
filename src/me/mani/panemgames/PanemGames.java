package me.mani.panemgames;

import me.mani.panemgames.commands.HologramCommand;
import me.mani.panemgames.commands.PingCommand;
import me.mani.panemgames.commands.RemovePointCommand;
import me.mani.panemgames.commands.SetPointCommand;
import me.mani.panemgames.config.ConfigManager;
import me.mani.panemgames.gamestate.GameStateManager;
import me.mani.panemgames.gamestate.Lobby;
import me.mani.panemgames.holograms.Hologram;
import me.mani.panemgames.listener.ChatListener;
import me.mani.panemgames.listener.EntityDamageByEntityListener;
import me.mani.panemgames.listener.EntityShootBowListener;
import me.mani.panemgames.listener.PlayerDeathListener;
import me.mani.panemgames.listener.PlayerInteractEntityListener;
import me.mani.panemgames.listener.PlayerJoinListener;
import me.mani.panemgames.listener.PlayerLiveChangeListener;
import me.mani.panemgames.listener.PlayerToggleSneakListener;
import me.mani.panemgames.mysql.DatabaseManager;
import me.mani.panemgames.util.PacketCamera;
import me.mani.panemgames.util.PacketManager;
import me.mani.panemgames.util.PacketWorldBorder;
import net.minecraft.server.v1_7_R4.EnumProtocol;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scoreboard.DisplaySlot;

public class PanemGames extends JavaPlugin implements Listener {

	private static PanemGames instance;
	
	private PlayerScoreboardManager playerScoreboardManager;
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
		Bukkit.getPluginManager().registerEvents(new PlayerInteractEntityListener(this), this);
		Bukkit.getPluginManager().registerEvents(new ChatListener(this), this);
		Bukkit.getPluginManager().registerEvents(new PlayerDeathListener(this), this);
		Bukkit.getPluginManager().registerEvents(new PlayerToggleSneakListener(this), this);
		Bukkit.getPluginManager().registerEvents(new PlayerLiveChangeListener(this), this);
		Bukkit.getPluginManager().registerEvents(new EntityShootBowListener(this), this);
		
		// Load Locations
		
		LocationManager.loadAll();
		
		// Scoreboard
		
		playerScoreboardManager = new PlayerScoreboardManager("§e- PanemGames -", DisplaySlot.SIDEBAR);
		
		// ConfigManager
		
		new ConfigManager(this);

		// ItemManager
		
		new ItemManager();
		
		// Hologram
		
		welcomeHologram = new Hologram("welcomeHologram", LocationManager.getLocation("lobbyWelcome").getLocation());
		welcomeHologram.addLine("§7Willkommen bei §ePanemGames");
		
		// Packets
		
		PacketManager.registerPacket(EnumProtocol.PLAY, true, 67, PacketCamera.class);
		PacketManager.registerPacket(EnumProtocol.PLAY, true, 68, PacketWorldBorder.class);
		
		// Datenbank
		
		DatabaseManager databaseManager = new DatabaseManager();
		databaseManager.setup("localhost", "3306", "minecraft", "root", "legoman");
		
		// PanemPlayer
		
		PlayerManager.createPanemPlayers((Player[]) PlayerManager.getAll().toArray());
		
		// Starte Lobbyphase
		
		PlayerManager.sendAll("§7[§ePanemGames§7] §8Willkommen bei §cPanemGames");
		gameStateManager = new GameStateManager(new Lobby());
	}
	
	@Override
	public void onDisable() {
		
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
	
	public GameStateManager getGameStateManager() {
		return this.gameStateManager;
	}
	
	public enum NewBlock {
		SLIME_BLOCK (165, 0),
		BARRIER (166, 0),
		IRON_TRAPDOOR (167, 0),
		GRANITE (1, 1),
		POLISHED_GRANITE (1, 2),
		DIORITE (1, 3),
		POLISHED_DIORITE (1, 4),
		ANDESITE (1, 5),
		POLISHED_ANDESITE (1, 6),
		PRISMARINE (168, 0),
		PRISMARINE_BRICKS (168, 1),
		DARK_PRISMARINE (168, 2),
		SEALANTERN (169, 0),
		RED_SANDSTONE (179, 0),
		CHISELED_RED_SANDSTONE (179, 1),
		SMOOTH_RED_SANDSTONE (179, 2),
		SPRUCE_FENCE (188, 0),
		BIRCH_FENCE (189, 0),
		JUNGLE_FENCE (190, 0),
		DARK_OAK_FENCE (191, 0),
		ACACIA_FENCE (192, 0),
		SPRUCE_DOOR (193, 0),
		BIRCH_DOOR (194, 0),
		JUNGLE_DOOR (195, 0),
		DARK_OAK_DOOR (196, 0),
		ACACIA_DOOR (197, 0);
		
		private int id;
		private short data;
		
		private NewBlock(int id, int data) {
			this.id = id;
			this.data = (short) data;
		}
		
		public int getId() {
			return this.id;
		}
		
		public short getData() {
			return this.data;
		}
		
	}
	
}
