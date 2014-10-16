package me.mani.panemgames.gamestate;

import me.mani.panemgames.PanemGames;

import org.bukkit.Bukkit;
import org.bukkit.event.HandlerList;

public class GameStateManager {
	
	private static GameStateComponent currentGameStateComponent;
	
	public GameStateManager(GameStateComponent firstGameStateComponent) {
		Bukkit.getPluginManager().registerEvents(firstGameStateComponent, PanemGames.getPanemGames());
		firstGameStateComponent.start();
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
		currentGameStateComponent = newGameStateComponent;
	}
	
	// ENUM - GameState
	
	public enum GameState {
		LOBBY, WARM_UP, INGAME;
	}
}