package me.mani.panemgames.gamestate;

import java.util.HashMap;

import me.mani.panemgames.PanemListenerManager.PanemEvent;
import me.mani.panemgames.PanemListenerManager.PanemListener;

import org.bukkit.event.Listener;

public class GameStateManager {
	
	private static HashMap<GameState, GameStateComponent> allGameStateComponents = new HashMap<>();
	
	private static GameState currentGameState = null;
	
	public GameStateManager() {
		
	}
	
	public static GameState getCurrentGameState() {
		return currentGameState;
	}
	
	public static void setCurrentGameState(GameState gameState) {
		currentGameState = gameState;
	}
	
	private static GameStateComponent getGameStateComponent(GameState gameState) {
		return allGameStateComponents.get(gameState);
	}
	
	// ENUM - GameState
	
	public enum GameState {
		LOBBY, WARM_UP, INGAME;
	}
	
	// SUPERCLASS - GameStateComponent
	
	public static class GameStateComponent implements Listener {
		
		private GameState gameState;
		
		public GameStateComponent(GameState gameState) {
			this.gameState = gameState;
		}
		
		public void start() {
			GameStateManager.setCurrentGameState(gameState);
		}
		
		public void finish(GameStateComponent nextGameStateComponent) {
			
			nextGameStateComponent.start();
		}
		
		private GameState getGameState() {
			return this.gameState;
		}
		
	}
	
	// INTERFACE - GameStateChangeListener
	
	public interface GameStateChangeListener extends PanemListener {
		
		public void onGameStateChange(GameStateChangeEvent ev);
		
	}
	
	// CLASS - GameStateChangeEvent
	
	public class GameStateChangeEvent implements PanemEvent {
		
		private GameStateComponent fromGameStateComponent;
		private GameStateComponent toGameStateComponent;
		
		public GameStateChangeEvent(GameStateComponent fromGameStateComponent, GameStateComponent toGameStateComponent) {
			this.fromGameStateComponent = fromGameStateComponent;
			this.toGameStateComponent = toGameStateComponent;
		}
		
		public GameStateComponent getOldGameStateComponent() {
			return fromGameStateComponent;
		}
		
		public GameStateComponent getNewGameStateComponent() {
			return toGameStateComponent;
		}
		
	}

}