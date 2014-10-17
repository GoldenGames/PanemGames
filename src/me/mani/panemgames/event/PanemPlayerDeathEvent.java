package me.mani.panemgames.event;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import me.mani.panemgames.PanemPlayer;

public class PanemPlayerDeathEvent extends Event {

	private PanemPlayer p;
	private DeathCause cause;
	private String deathMessage;
	
	public PanemPlayerDeathEvent(PanemPlayer p, DeathCause cause) {
		this(p, cause, "");
	}
	
	public PanemPlayerDeathEvent(PanemPlayer p, DeathCause cause, String deathMessage) {
		this.p = p;
		this.cause = cause;
		this.deathMessage = deathMessage;
	}
	
	public PanemPlayer getPanemPlayer() {
		return p;
	}
	
	public DeathCause getCause() {
		return cause;
	}
	
	public String getDeathMessage() {
		return this.deathMessage;
	}
	
	public void setDeathMessage(String deathMessage) {
		this.deathMessage = deathMessage;
	}
	
	public enum DeathCause {
		TRIBUTE,
		NATURE,
		TRAP;
	}

	private static final HandlerList handlers = new HandlerList();
	 
	public HandlerList getHandlers() {
	    return handlers;
	}
	 
	public static HandlerList getHandlerList() {
	    return handlers;
	}
}
