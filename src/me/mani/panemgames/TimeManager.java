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
		SUNRISE ("Morgen"), MIDDAY ("Mittag"), AFTERNOON ("Nachmittag"), SUNSET ("Abend"), NIGHT ("Nacht"), OTHER ("Unbekannt");
		
		private String name;
		
		private Time(String name) {
			this.name = name;
		}
		
		public String getName() {
			return this.name;
		}
	}

	public void update() {
		currentTime = getTime(world.getTime());
		if (s == null) {
			s = pl.getPlayerScoreboardManager().addValueAll("§e" + currentTime.getName(), 4);
			return;
		}
		if (s.getEntry().equals("§e" + currentTime.getName()))
			return;
		pl.getPlayerScoreboardManager().resetScoreAll(4);
		s = pl.getPlayerScoreboardManager().addValueAll("§e" + currentTime.getName(), 4);
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
