package me.mani.panemgames;

import me.mani.panemgames.CountdownManager.Countdown;
import me.mani.panemgames.event.countdown.CountdownCountEvent;

public abstract class CountdownCallback {
	
	private Countdown countdown;

	public void boundToCountdown(Countdown countdown) {
		this.countdown = countdown;
	}
	
	public abstract void onCountdownFinish();
	
	public abstract void onCountdownCount(CountdownCountEvent ev);
	
	public void cancelCountdown() {
		countdown.stop();
	}

}
