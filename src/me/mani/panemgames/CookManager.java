package me.mani.panemgames;

import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class CookManager {
	
	public static void startCookTask(Player p) {
		new CookingTask(p).runTaskTimer(PanemGames.getPanemGames(), 0L, 5L);
	}
	
	@SuppressWarnings("deprecation")
	private static class CookingTask extends BukkitRunnable {

		public CookingTask(Player p) {
			this.p = p;
		}
		
		Player p;
		int i = 0;
		
		@Override
		public void run() {
			if (!p.getTargetBlock(null, 2).getType().equals(Material.FIRE) || !ItemManager.isCookable(p.getItemInHand().getType())) {
				this.cancel();
				return;
			}
			if (i == 12) {
				p.playSound(p.getLocation(), Sound.FIZZ, 1, 1);
				p.getItemInHand().setType(Material.getMaterial(p.getItemInHand().getTypeId() + 1));
				this.cancel();
				return;
			}
			i++;
		}
		
	}

}
