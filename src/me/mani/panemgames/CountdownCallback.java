package me.mani.panemgames;

import me.mani.panemgames.event.countdown.CountdownCountEvent;

public interface CountdownCallback {
	
	public void onCountdownFinish();
	
	public void onCountdownCount(CountdownCountEvent ev);

}
