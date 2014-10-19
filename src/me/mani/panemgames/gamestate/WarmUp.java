package me.mani.panemgames.gamestate;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Random;

import me.mani.panemgames.CountdownCallback;
import me.mani.panemgames.CountdownManager;
import me.mani.panemgames.CountdownManager.Countdown;
import me.mani.panemgames.LocationManager;
import me.mani.panemgames.PanemPlayer;
import me.mani.panemgames.PlayerManager;
import me.mani.panemgames.effects.ParticleEffect;
import me.mani.panemgames.event.PanemPlayerDeathEvent;
import me.mani.panemgames.event.countdown.CountdownCountEvent;
import me.mani.panemgames.gamestate.GameStateManager.GameState;
import net.minecraft.util.io.netty.channel.rxtx.RxtxChannelConfig.Stopbits;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.craftbukkit.v1_7_R4.util.UnsafeList.Itr;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.WitherSkull;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.util.Vector;

public class WarmUp extends GameStateComponent {
	
	private Iterator<Player> allPlayer;

	public WarmUp() {
		super(GameState.WARM_UP);
	}
	
	@Override
	public void start() {
		teleportAll();
	}
	
	public void teleportAll() {
		
		allPlayer = PlayerManager.getAll().iterator();
		
		// Teleportcountdown 
		
		CountdownManager.createCountdown(new CountdownCallback() {
			
			@Override
			public void onCountdownFinish() {
				startWarmUp();
			}
			
			@Override
			public void onCountdownCount(CountdownCountEvent ev) {
				if (LocationManager.getSpawnLocations(ev.getCurrentNumber()) == null) {
					cancelCountdown();
					return;
				}
				if (allPlayer.hasNext())
					allPlayer.next().teleport(LocationManager.getSpawnLocations(ev.getCurrentNumber()).getLocation());
				else
					cancelCountdown();
			}
			
		}, 1, 24, 5L);
	}
	
	public void startWarmUp() {
				
		// Countdown
		
		CountdownManager.createCountdown(new CountdownCallback() {

			@Override
			public void onCountdownFinish() {
				PlayerManager.sendAll(" \n");
				PlayerManager.sendAll("§7[§ePanemGames§7] §eJetzt sollte PanemGames starten");
			}
			
			@Override
			public void onCountdownCount(CountdownCountEvent ev) {
				PlayerManager.sendAll(" \n \n \n" + "§7[§ePanemGames§7] §8Noch §b" + ev.getCurrentNumber() + " §8Sekunden bis zum Start!" + "\n \n \n ");
				for (Player p : PlayerManager.getAll())
						ParticleEffect.LARGE_SMOKE.display(2, 2, 2, 1, 10, p.getLocation(), 100);
			}
			
		}, 10, 0, 20L);
	}
	
	@EventHandler
	public void onDeath(PanemPlayerDeathEvent ev) {
		PanemPlayer p = ev.getPanemPlayer();
		p.setAlive(false);
		PlayerManager.playAll(Sound.AMBIENCE_THUNDER);
	}
	
	@EventHandler
	public void onMove(PlayerMoveEvent ev) {
		Player p = ev.getPlayer();
		if (ev.getTo().getBlock().getType() == Material.WOOD_PLATE || ev.getTo().getBlock().getType() == Material.STONE_PLATE) {
			ParticleEffect.LARGE_EXPLODE.display(2, 2, 2, 1, 10, ev.getTo(), 20);
			ev.getTo().getBlock().setType(Material.AIR);
			Random random = new Random();
			Vector pushFrom = ev.getTo().toVector();
			Vector pushTo = ev.getTo().getBlock().getLocation().add(0.5, 0.5, 0.5).toVector();
			p.setVelocity(pushFrom.subtract(pushTo).normalize().multiply(random.nextInt(2) + 3));
			p.playSound(p.getLocation(), Sound.EXPLODE, 2, 5);
			p.setHealth(0.0);
		}
	}
}
