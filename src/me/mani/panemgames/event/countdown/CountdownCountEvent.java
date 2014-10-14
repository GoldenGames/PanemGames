package me.mani.panemgames.event.countdown;

import org.bukkit.Sound;

public class CountdownCountEvent {
	
	public CountdownCountEvent(int currentNumber) {
		this.currentNumber = currentNumber;
	}
	
	private int currentNumber;
	
	private String message;
	private Sound sound;	
	
	public void setMessage(String message) {
		this.message = message;
	}
	
	public boolean hasMessage() {
		return message != null;
	}
	
	public String getMessage() {
		return message;
	}
	
	public void setSound(Sound sound) {
		this.sound = sound;
	}
	
	public boolean hasSound() {
		return sound != null;
	}
	
	public Sound getSound() {
		return sound;
	}
	
	public int getCurrentNumber() {
		return this.currentNumber;
	}

}
