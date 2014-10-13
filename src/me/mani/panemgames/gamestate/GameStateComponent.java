package me.mani.panemgames.gamestate;

import me.mani.panemgames.PanemGames;
import me.mani.panemgames.event.events.GameStateChangeEvent;
import me.mani.panemgames.gamestate.GameStateManager.GameState;

import org.bukkit.Bukkit;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;

public abstract class GameStateComponent implements Listener {
	
	private GameStateManager gameStateManager;
	private GameState gameState;
	
	public GameStateComponent(PanemGames pl, GameState gameState) {
		this.gameStateManager = pl.getGameStateManager();
		this.gameState = gameState;
	}
	
	public abstract void start();
	
	public void finish(GameStateComponent nextGameStateComponent) {
		GameStateChangeEvent ev = new GameStateChangeEvent(this, nextGameStateComponent);
		Bukkit.getPluginManager().callEvent(ev);
		HandlerList.unregisterAll(ev.getOldGameStateComponent());
		Bukkit.getPluginManager().registerEvents(ev.getNewGameStateComponent(), PanemGames.getPanemGames());
		ev.getNewGameStateComponent().start();
		gameStateManager.setCurrentGameStateComponent(ev.getNewGameStateComponent());
	}
	
	public GameStateManager getManager() {
		return this.gameStateManager;
	}
	
	public GameState getGameState() {
		return this.gameState;
	}

}
