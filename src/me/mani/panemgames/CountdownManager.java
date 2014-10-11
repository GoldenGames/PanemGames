package me.mani.panemgames;

import java.util.Arrays;
import java.util.List;

import org.bukkit.Sound;
import org.bukkit.scheduler.BukkitRunnable;

public class CountdownManager {
	
	private PanemGames pl;

	public CountdownManager(PanemGames pl) {
		this.pl = pl;
	}
	
	/**
    * Startet einen Countdown.
    *
    * @param callback Die Callback Methode
    * @param from Die Zeit (Anfang)
    * @param to Die Zeit (Ende)
    * @param sound Der Sound der abgespielt werden soll
    * @param pattern Der Text ([time] -> Die verbleibende Zeit) 
    */
	
	public Countdown createCountdown(CountdownCallback callback, int from, int to) {
		Countdown c = new Countdown(from, to, callback);
		c.runTaskTimer(pl, 0L, 20L);
		return c;
	}
	
	public class Countdown extends BukkitRunnable {

		public Countdown(int from, int to, CountdownCallback callback) {
			this.from = from;
			this.to = to;
			this.callback = callback;
			
			this.downcounting = (from > to);
		}
		
		private int from;
		private int to;
		private CountdownCallback callback;
		
		private boolean downcounting;
		
		@Override
		public void run() {
			CountdownCountEvent ev = new CountdownCountEvent(from);
			callback.onCountdownCount(ev);
			if (ev.hasMessage())
				PlayerManager.sendAll(ev.getMessage());
			if (ev.hasSound())
				PlayerManager.playAll(ev.getSound());
			if (downcounting) {
				if (from != to)
					from--;
				else
					stop();
			}
			else {
				if (from != to)
					from++;
				else
					stop();
			}
		}	
		
		private void stop() {
			this.cancel();
			callback.onCountdownFinish();
		}
		
		public void forceStop() {
			this.cancel();
		}
		
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
	}
}
