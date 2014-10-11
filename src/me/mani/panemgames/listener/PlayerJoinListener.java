package me.mani.panemgames.listener;

import java.lang.reflect.InvocationTargetException;

import me.mani.panemgames.LocationManager;
import me.mani.panemgames.PanemGames;
import me.mani.panemgames.PlayerScoreboardManager;
import me.mani.panemgames.TemperatureManager;
import me.mani.panemgames.UpdatingScheduler;
import net.minecraft.server.v1_7_R4.ChatSerializer;
import net.minecraft.server.v1_7_R4.IChatBaseComponent;

import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_7_R4.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.spigotmc.ProtocolInjector.PacketTabHeader;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import com.comphenix.protocol.events.PacketContainer;

public class PlayerJoinListener implements Listener {
	
	private PanemGames pl;

	public PlayerJoinListener(PanemGames pl) {
		this.pl = pl;
	}
	
	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent ev) {
		Player p = ev.getPlayer();
		
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
		
		// Test Armor Stand
		
		if(((CraftPlayer) p).getHandle().playerConnection.networkManager.getVersion() >= 47) {
			ProtocolManager manager = ProtocolLibrary.getProtocolManager();
			Location loc = p.getLocation();
			
			PacketContainer packet = manager.createPacket(PacketType.Play.Server.SPAWN_ENTITY);
			packet.getIntegers().
			write(0, 10000).
			write(1, (int) Math.floor(loc.getX() * 32.0D)).
			write(2, (int) Math.floor(loc.getY() * 32.0D)).
			write(3, (int) Math.floor(loc.getZ() * 32.0D)).
			write(9, 30);
			
	        try {
				manager.sendServerPacket(p, packet);
			} catch (InvocationTargetException e1) {
				System.out.println("Cannot send Packet! Error: ");
				e1.printStackTrace();
			}
		}
	}

}
