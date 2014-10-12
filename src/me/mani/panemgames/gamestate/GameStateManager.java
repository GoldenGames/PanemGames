package me.mani.panemgames.gamestate;

import java.util.HashMap;

import org.bukkit.event.Listener;

public class GameStateManager {
	
	private static HashMap<GameState, GameStateComponent> allGameStateComponents = new HashMap<>();
	
	private static GameState currentGameState = GameState.NULL;
	private static GameStateComponent currentGameStateComponent;
	
	public GameStateManager(HashMap<GameState, GameStateComponent> allGameStateComponents) {
		GameStateManager.allGameStateComponents = allGameStateComponents;
	}
	
	public static GameState getCurrentGameState() {
		return currentGameState;
	}
	
	private static GameStateComponent getGameStateComponent(GameState gameState) {
		return allGameStateComponents.get(gameState);
	}
	
	public static void setCurrentGameState(GameState gameState) {
		currentGameState = gameState;
	}
	
	public enum GameState {
		NULL, LOBBY, WARP_UP, INGAME;
	}
	
	public interface GameStateComponent extends Listener {
		
		public void start(GameState gameState);
		
		public GameState finish();
		
	}

}