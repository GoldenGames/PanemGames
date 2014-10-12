package me.mani.panemgames.gamestate;

import java.util.Random;

import me.mani.panemgames.CountdownCallback;
import me.mani.panemgames.CountdownManager;
import me.mani.panemgames.CountdownManager.Countdown;
import me.mani.panemgames.CountdownManager.Countdown.CountdownCountEvent;
import me.mani.panemgames.LocationManager;
import me.mani.panemgames.PanemGames;
import me.mani.panemgames.PlayerManager;
import me.mani.panemgames.effects.ParticleEffect;

import org.bukkit.GrassSpecies;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.material.FlowerPot;
import org.bukkit.material.LongGrass;

public class Lobby implements Listener {
	
	private CountdownManager countdownManager;	
	private Countdown lobbyCountdown;

	public Lobby(PanemGames pl) {
		this.countdownManager = pl.getCountdownManager();
	}
	
	public void start() {
		
	}
	
	public void startCountdown() {
		lobbyCountdown = countdownManager.createCountdown(new CountdownCallback() {
			
			@Override
			public void onCountdownFinish() {
				startLobbyMinigame();
			}

			@Override
			public void onCountdownCount(CountdownCountEvent ev) {
				ev.setMessage("�7[�ePanemGames�7] �8In �e" + ev.getCurrentNumber() + " �8Sekunden k�nnt ihr die Sponsoren beeindrucken!");
				ev.setSound(Sound.ORB_PICKUP);
			}
			
		}, 20, 0);
	}
	
	public void stopCountdown() {
		lobbyCountdown.forceStop();
	}
	
	public void startLobbyMinigame() {
		PlayerManager.sendAll("�7[�ePanemGames�7] �8Sammle so viel �eErfahrung �8wie m�glich auf!");
		spawnExp();
		countdownManager.createCountdown(new CountdownCallback() {
			
			@Override
			public void onCountdownFinish() {
				start();
			}

			@Override
			public void onCountdownCount(CountdownCountEvent ev) {
				ev.setMessage("�7[�ePanemGames�7] �8Das Spiel startet in �e" + ev.getCurrentNumber() + " �8Sekunden");
				ev.setSound(Sound.NOTE_BASS);
			}
			
		}, 20, 0);
	}
	
	@SuppressWarnings("deprecation")
	public void spawnExp() {
		Location centerLocation = LocationManager.getLocation("lobbyExp").getLocation();
		for (int x = -10; x < 10; x++) {
			for (int z = -10; z < 10; z++) {
				Block b = centerLocation.getWorld().getHighestBlockAt(centerLocation.getBlockX() + x, centerLocation.getBlockZ() + z);
				b.setType(Material.AIR);
				Random random = new Random();
				int randomInt = random.nextInt(10);
				if (randomInt < 3) {
					b.setType(Material.FLOWER_POT);
					((FlowerPot) b.getState()).setContents(new LongGrass(GrassSpecies.FERN_LIKE));
				}
			}
		}
	}
	
	/*
	 * @args amount The amount of exp the Player will get (100 means Lvl-Up)
	 */
	public void addExp(int amount, Player p) {
		p.setExp(p.getExp() + (float) amount/100);
		if (p.getExp() >= 1) {
			p.setLevel(p.getLevel() + 1);
			p.setExp(p.getExp() - 1f);
		}
	}
	
	@EventHandler
	public void onEntityInteract(PlayerInteractEvent ev) {
		Player p = ev.getPlayer();
		if (ev.getAction() == Action.RIGHT_CLICK_BLOCK && ev.getClickedBlock().getType() == Material.FLOWER_POT) {	
			
			p.playSound(p.getLocation(), Sound.ORB_PICKUP, 1, 1);
			addExp(10, p);
			
			ev.getClickedBlock().setType(Material.AIR);	
			p.playSound(p.getLocation(), Sound.DIG_STONE, 1, 1);
			ParticleEffect.displayBlockCrack(140, (byte) 0, 0, 0, 0, 30, ev.getClickedBlock().getLocation().add(0.5, 0.5, 0.5), 10);
			ev.setCancelled(true);
			
		}
	}
	
	@EventHandler
	public void onBlockBreak(BlockBreakEvent ev) {
		Player p = ev.getPlayer();
		if (ev.getBlock().getType() == Material.FLOWER_POT) {
			
			addExp(10, p);
			p.playSound(p.getLocation(), Sound.ORB_PICKUP, 1, 1);
			
			ev.getBlock().setType(Material.AIR);
			ev.setCancelled(true);
			
		}
	}

}
