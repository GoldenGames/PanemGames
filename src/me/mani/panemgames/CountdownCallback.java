package me.mani.panemgames;

import me.mani.panemgames.CountdownManager.Countdown.CountdownCountEvent;

public interface CountdownCallback {
	
	public void onCountdownFinish();
	
	public void onCountdownCount(CountdownCountEvent ev);

}
