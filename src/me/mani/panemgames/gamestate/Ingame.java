package me.mani.panemgames.gamestate;

import java.util.Random;

import me.mani.panemgames.PanemGames;
import me.mani.panemgames.PlayerManager;
import me.mani.panemgames.PlayerScoreboardManager;
import me.mani.panemgames.TemperatureManager;
import me.mani.panemgames.TimeManager;
import me.mani.panemgames.UpdatingScheduler;
import me.mani.panemgames.effects.ParticleEffect;
import me.mani.panemgames.gamestate.GameStateManager.GameState;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.util.Vector;

public class Ingame extends GameStateComponent {

	private TimeManager timeManager;
	
	public Ingame() {
		super(GameState.INGAME);
	}

	@Override
	public void start() {
		PlayerManager.sendAll("§7[ §aLasset die Spiele beginnen! §7]\n§7[ §eUnd möge das Glück stets mit euch sein! §7]");
		
	}
	
	@Override
	public void setupScoreboard(PlayerScoreboardManager playerScoreboardManager) {
		playerScoreboardManager.resetAllScoresAll();
		
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
		
		timeManager = new TimeManager(PanemGames.getPanemGames(), Bukkit.getWorld("world"));
				
		// UpdatingManager
				
		UpdatingScheduler updatingScheduler = new UpdatingScheduler();
		UpdatingScheduler.add(timeManager);
		updatingScheduler.startUpdatingSchedule(PanemGames.getPanemGames());
				
		// Temperature Scheduler
		
		for (Player p : PlayerManager.getAll())
			UpdatingScheduler.add(new TemperatureManager(PanemGames.getPanemGames(), p));
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
