package me.mani.panemgames.event.events;

import me.mani.panemgames.gamestate.GameStateComponent;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class GameStateChangeEvent extends Event {

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
	
	// Handlers
	
	private static final HandlerList handlers = new HandlerList();

	public HandlerList getHandlers() {
		return handlers;
	}

	public static HandlerList getHandlerList() {
		return handlers;
	}

}
