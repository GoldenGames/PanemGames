package me.mani.panemgames;

import java.lang.reflect.Field;

import me.mani.panemgames.commands.HologramCommand;
import me.mani.panemgames.commands.PingCommand;
import me.mani.panemgames.commands.RemovePointCommand;
import me.mani.panemgames.commands.SetPointCommand;
import me.mani.panemgames.config.ConfigManager;
import me.mani.panemgames.gamestate.GameStateManager;
import me.mani.panemgames.gamestate.Lobby;
import me.mani.panemgames.gamestate.WarmUp;
import me.mani.panemgames.holograms.Hologram;
import me.mani.panemgames.listener.EntityDamageByEntityListener;
import me.mani.panemgames.listener.PlayerJoinListener;
import net.minecraft.server.v1_7_R4.Block;
import net.minecraft.server.v1_7_R4.PacketPlayOutBlockChange;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_7_R4.CraftWorld;
import org.bukkit.craftbukkit.v1_7_R4.entity.CraftPlayer;
import org.bukkit.entity.Player;
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
		playerScoreboardManager.addValueAll("§6██████████", 4);		//	4
		playerScoreboardManager.addValueAll("§3", 3);				//	3
		playerScoreboardManager.addValueAll("§7Körper:", 2);		//	2
																	//	1
		
		// TimeManager
		
		timeManager = new TimeManager(this, Bukkit.getWorld("world"));
		
		// UpdatingManager
		
		UpdatingScheduler updatingScheduler = new UpdatingScheduler();
		UpdatingScheduler.add(timeManager);
		updatingScheduler.startUpdatingSchedule(this);
		
		// ConfigManager
		
		new ConfigManager(this);

		// ItemManager
		
		new ItemManager();
		
		// Hologram
		
		welcomeHologram = new Hologram("welcomeHologram", LocationManager.getLocation("lobbyWelcome").getLocation());
		welcomeHologram.addLine("§7Willkommen bei §ePanemGames");
		
		// Starte Lobbyphase
		
		PlayerManager.sendAll("§7[§ePanemGames§7] §8Willkommen bei §cPanemGames");
		// Lobby lobby = new Lobby();
		gameStateManager = new GameStateManager(new WarmUp());
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
	
	public TimeManager getTimeManager() {
		return this.timeManager;
	}
	
	public GameStateManager getGameStateManager() {
		return this.gameStateManager;
	}
	
	public void changeBlockState(Location loc, NewBlock newBlock) {
		PacketPlayOutBlockChange blockChange = new PacketPlayOutBlockChange(loc.getBlockX(), loc.getBlockY(), loc.getBlockZ(), ((CraftWorld) loc.getWorld()).getHandle());
		
		try {
			Field blockField = PacketPlayOutBlockChange.class.getDeclaredField("block");
			blockField.setAccessible(true);
			blockField.set(blockChange, Block.getById(newBlock.getId()));
		
			Field dataField = PacketPlayOutBlockChange.class.getDeclaredField("data");
			dataField.setAccessible(true);
			dataField.set(blockChange, newBlock.getData());
		} catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException e) {
			e.printStackTrace();
		}
		
		for (Player p : PlayerManager.getAll())
			((CraftPlayer) p).getHandle().playerConnection.sendPacket(blockChange);
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
		private int data;
		
		private NewBlock(int id, int data) {
			this.id = id;
			this.data = data;
		}
		
		public int getId() {
			return this.id;
		}
		
		public int getData() {
			return this.data;
		}
		
	}
	
}
