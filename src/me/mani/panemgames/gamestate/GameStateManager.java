package me.mani.panemgames.gamestate;

import me.mani.panemgames.PanemGames;
import me.mani.panemgames.event.gamestate.GameStateChangeEvent;

import org.bukkit.Bukkit;
import org.bukkit.event.HandlerList;


public class GameStateManager {
	
	private PanemGames pl;
	private GameStateComponent currentGameStateComponent;
	
	public GameStateManager(PanemGames pl, GameStateComponent firstGameStateComponent) {
		this.pl = pl;
		Bukkit.getPluginManager().registerEvents(firstGameStateComponent, pl);
		firstGameStateComponent.start();
		this.currentGameStateComponent = firstGameStateComponent;
	}
	
	public PanemGames getPlugin() {
		return this.pl;
	}
	
	public GameStateComponent getCurrentGameStateComponent() {
		return currentGameStateComponent;
	}
	
	public void setCurrentGameStateComponent(GameStateComponent gameStateComponent) {
		currentGameStateComponent = gameStateComponent;
	}
	
	public void switchGameStateComponent(GameStateComponent newGameStateComponent) {
		GameStateChangeEvent ev = new GameStateChangeEvent(currentGameStateComponent, newGameStateComponent);
		Bukkit.getPluginManager().callEvent(ev);
		HandlerList.unregisterAll(ev.getOldGameStateComponent());
		Bukkit.getPluginManager().registerEvents(ev.getNewGameStateComponent(), pl);
		ev.getNewGameStateComponent().start();
		this.setCurrentGameStateComponent(ev.getNewGameStateComponent());
	}
	
	// ENUM - GameState
	
	public enum GameState {
		LOBBY, WARM_UP, INGAME;
	}
}