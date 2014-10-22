package me.mani.panemgames.event;

import me.mani.panemgames.AttackCause;
import me.mani.panemgames.PanemPlayer;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class PanemPlayerDeathEvent extends Event {

	private PanemPlayer p;
	private AttackCause cause;
	private String deathMessage;
	
	public PanemPlayerDeathEvent(PanemPlayer p, AttackCause cause) {
		this(p, cause, "");
	}
	
	public PanemPlayerDeathEvent(PanemPlayer p, AttackCause cause, String deathMessage) {
		this.p = p;
		this.cause = cause;
		this.deathMessage = deathMessage;
	}
	
	public PanemPlayer getPanemPlayer() {
		return p;
	}
	
	public AttackCause getCause() {
		return cause;
	}
	
	public String getDeathMessage() {
		return this.deathMessage;
	}
	
	public void setDeathMessage(String deathMessage) {
		this.deathMessage = deathMessage;
	}
	
	private static final HandlerList handlers = new HandlerList();
	 
	public HandlerList getHandlers() {
	    return handlers;
	}
	 
	public static HandlerList getHandlerList() {
	    return handlers;
	}
}
