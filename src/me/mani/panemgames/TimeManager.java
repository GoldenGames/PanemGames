package me.mani.panemgames;

import org.bukkit.World;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;
import org.bukkit.scoreboard.Score;

public class TimeManager implements Updatable {
	
	private PanemGames pl;
	
	private World world;	
	private Time currentTime;
	
	private Score s;
	
	public TimeManager(PanemGames pl, World world) {
		this.pl = pl;
		this.world = world;
	}
	
	enum Time {
		SUNRISE ("Morgen", 12, 17),
		MIDDAY ("Mittag", 17, 23),
		AFTERNOON ("Nachmittag", 23, 27),
		SUNSET ("Abend", 13, 16),
		NIGHT ("Nacht", 5, 10),
		OTHER ("Unbekannt", 5, 10);
		
		private String name;
		private int minTemperature;
		private int maxTemperature;
		
		private Time(String name, int minTemperature, int maxTemperature) {
			this.name = name;
			this.minTemperature = minTemperature;
			this.maxTemperature = maxTemperature;	
		}
		
		public String getName() {
			return this.name;
		}
		
		public int getMinTemperature() {
			return this.minTemperature;
		}
		
		public int getMaxTemperature() {
			return this.maxTemperature;
		}		
	}

	public void update() {
		currentTime = getTime(world.getTime());
		if (s == null) {
			s = pl.getPlayerScoreboardManager().addValueAll("§e" + currentTime.getName(), 10);
			return;
		}
		if (s.getEntry().equals("§e" + currentTime.getName()))
			return;
		pl.getPlayerScoreboardManager().resetScoreAll(s);
		s = pl.getPlayerScoreboardManager().addValueAll("§e" + currentTime.getName(), 10);
	}
		
	private Time getTime(long time) {
		if ((time > 23000 && 24000 >= time) || (time > 0 && 3999 >= time))
			return Time.SUNRISE;
		else if (time > 4000 && 6999 >= time)
			return Time.MIDDAY;
		else if (time > 7000 && 10999 >= time)
			return Time.AFTERNOON;
		else if (time > 11000 && 15999 >= time)
			return Time.SUNSET;
		else if (time > 16000 && 22999 >= time)
			return Time.NIGHT;
		else
			return Time.OTHER;
	}

	public Time getCurrentTime() {
		return currentTime;
	}
	
}
