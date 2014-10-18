package me.mani.panemgames.listener;

import me.mani.panemgames.LocationManager;
import me.mani.panemgames.PanemGames;
import me.mani.panemgames.PanemPlayer;
import me.mani.panemgames.PlayerManager;
import me.mani.panemgames.PlayerScoreboardManager;
import me.mani.panemgames.TemperatureManager;
import me.mani.panemgames.Title;
import me.mani.panemgames.UpdatingScheduler;
import net.minecraft.server.v1_7_R4.ChatSerializer;
import net.minecraft.server.v1_7_R4.IChatBaseComponent;

import org.bukkit.craftbukkit.v1_7_R4.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.spigotmc.ProtocolInjector.PacketTabHeader;

public class PlayerJoinListener implements Listener {
	
	private PanemGames pl;

	public PlayerJoinListener(PanemGames pl) {
		this.pl = pl;
	}
	
	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent ev) {
		Player p = ev.getPlayer();
		
		// PanemPlayer
		
		new PanemPlayer(p, null);
		
		// Scoreboard
		
		pl.getPlayerScoreboardManager().addPlayerScoreboard(p);
		p.setScoreboard(PlayerScoreboardManager.getPlayerScoreboard(p).getScoreboard());
		
		// Temperature Scheduler
		
		UpdatingScheduler.add(new TemperatureManager(pl, p));	
		
		// Lobby Teleport
		
		p.teleport(LocationManager.getLocation("lobbySpawn").getLocation());
		
		// Tablist
		
		if(((CraftPlayer) p).getHandle().playerConnection.networkManager.getVersion() >= 47) {
			IChatBaseComponent tabTitle = ChatSerializer.a("{\"text\": \"§7Hi §e" + p.getName() + " §7und Willkommen bei\"}");
	        IChatBaseComponent tabFooter = ChatSerializer.a("{\"text\": \"§7[§e PanemGames §7]\"}");
	         
	        PacketTabHeader tabHeader= new PacketTabHeader(tabTitle, tabFooter);
	         
	        ((CraftPlayer) p).getHandle().playerConnection.sendPacket(tabHeader);
		}
	}

}
