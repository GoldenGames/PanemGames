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
	
	public Countdown runCountdown(CountdownCallback callback, int from, int to, Sound sound, String pattern) {
		Countdown c = new Countdown(from, to, callback, pattern, sound, 45,30,15,10,5,3,2,1,0);
		c.runTaskTimer(pl, 0L, 20L);
		return c;
	}
	
	public class Countdown extends BukkitRunnable {

		public Countdown(int from, int to, CountdownCallback callback, String pattern, Sound sound, Integer... showNumbers) {
			this.from = from;
			this.to = to;
			this.callback = callback;
			this.pattern = pattern;
			this.sound = sound;
			this.showNumbers = Arrays.asList(showNumbers);
			
			this.downcounting = (from > to);
		}
		
		private int from;
		private int to;
		private CountdownCallback callback;
		private String pattern;
		private Sound sound;
		private List<Integer> showNumbers;
		
		private boolean downcounting;
		
		@Override
		public void run() {
			if (showNumbers.contains(from)) {
				PlayerManager.sendAll(pattern.replace("[time]", "" + from));
				PlayerManager.playAll(sound);
				
				Title title = new Title("", "\n§e" + from, 0, 30, 0);
				title.setTimingsToTicks();
				title.broadcast();
			}
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
	}
}
