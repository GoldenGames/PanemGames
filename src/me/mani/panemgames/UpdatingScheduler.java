package me.mani.panemgames;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

public class UpdatingScheduler {
	
	private static List<Updatable> updateble = new ArrayList<>();
	private BukkitTask schedulerTask;
	
	public static void add(Updatable updatable) {
		updateble.add(updatable);
	}
	
	public static void remove(Updatable updatable) {
		updateble.remove(updatable);
	}
	
	public void startUpdatingSchedule(PanemGames pl) {
		this.schedulerTask = new SchedulerThread().runTaskTimer(pl, 0L, 5L);
	}
	
	public void stopUpdatingSchedule() {
		this.schedulerTask.cancel();
	}
	
	private class SchedulerThread extends BukkitRunnable {
		
		@Override
		public void run() {
			for (Updatable u : updateble)
				u.update();
		}
		
	}

}
