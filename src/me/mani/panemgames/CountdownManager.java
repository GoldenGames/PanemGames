package me.mani.panemgames;

import me.mani.panemgames.event.countdown.CountdownCountEvent;

import org.bukkit.scheduler.BukkitRunnable;

public class CountdownManager {
	
	/**
    * Startet einen Countdown.
    *
    * @param callback Die Callback Methode
    * @param from Die Zeit (Anfang)
    * @param to Die Zeit (Ende)
    * @param sound Der Sound der abgespielt werden soll
    * @param pattern Der Text ([time] -> Die verbleibende Zeit) 
    */
	public static Countdown createCountdown(CountdownCallback callback, int from, int to, long speed) {
		Countdown c = new Countdown(from, to, callback);
		callback.boundToCountdown(c);
		c.runTaskTimer(PanemGames.getPanemGames(), 0L, speed);
		return c;
	}
	
	public static class Countdown extends BukkitRunnable {

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
		
		public void stop() {
			this.cancel();
			callback.onCountdownFinish();
		}
		
		public void forceStop() {
			this.cancel();
		}
	}
}
