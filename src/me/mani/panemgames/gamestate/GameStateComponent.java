package me.mani.panemgames.gamestate;

import me.mani.panemgames.PlayerScoreboardManager;
import me.mani.panemgames.gamestate.GameStateManager.GameState;

import org.bukkit.event.Listener;

public abstract class GameStateComponent implements Listener {
	
	private GameState gameState;
	
	public GameStateComponent(GameState gameState) {
		this.gameState = gameState;
	}
	
	public abstract void start();
	
	public abstract void setupScoreboard(PlayerScoreboardManager playerScoreboardManager);
	
	public void finish(GameStateComponent nextGameStateComponent) {
		GameStateManager.switchGameStateComponent(nextGameStateComponent);
	}
	
	public GameState getGameState() {
		return this.gameState;
	}

}
