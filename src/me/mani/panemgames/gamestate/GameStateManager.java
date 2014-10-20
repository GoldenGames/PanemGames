package me.mani.panemgames.gamestate;

import me.mani.panemgames.PanemGames;
import me.mani.panemgames.PlayerManager;
import me.mani.panemgames.PlayerScoreboardManager;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;

public class GameStateManager {
	
	private static GameStateComponent currentGameStateComponent;
	
	public GameStateManager(GameStateComponent firstGameStateComponent) {
		Bukkit.getPluginManager().registerEvents(firstGameStateComponent, PanemGames.getPanemGames());
		firstGameStateComponent.start();
		firstGameStateComponent.setupScoreboard(PanemGames.getPanemGames().getPlayerScoreboardManager());
		switchScoreboard(PanemGames.getPanemGames().getPlayerScoreboardManager());
		currentGameStateComponent = firstGameStateComponent;
	}
	
	public static GameStateComponent getCurrentGameStateComponent() {
		return currentGameStateComponent;
	}
	
	public static void setCurrentGameStateComponent(GameStateComponent gameStateComponent) {
		currentGameStateComponent = gameStateComponent;
	}
	
	public static void switchGameStateComponent(GameStateComponent newGameStateComponent) {
		HandlerList.unregisterAll(currentGameStateComponent);
		Bukkit.getPluginManager().registerEvents(newGameStateComponent, PanemGames.getPanemGames());
		newGameStateComponent.start();
		newGameStateComponent.setupScoreboard(PanemGames.getPanemGames().getPlayerScoreboardManager());
		switchScoreboard(PanemGames.getPanemGames().getPlayerScoreboardManager());
		currentGameStateComponent = newGameStateComponent;
	}
	
	private static void switchScoreboard(PlayerScoreboardManager playerScoreboardManager) {
		for (Player p : PlayerManager.getAll()) {
			playerScoreboardManager.addPlayerScoreboard(p);
			p.setScoreboard(PlayerScoreboardManager.getPlayerScoreboard(p).getScoreboard());
		}
	}
	
	// ENUM - GameState
	
	public enum GameState {
		LOBBY, WARM_UP, INGAME;
	}
}