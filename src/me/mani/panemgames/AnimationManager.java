package me.mani.panemgames;

import java.util.Map;
import java.util.Map.Entry;

import me.mani.panemgames.PlayerManager.ChatPosition;
import me.mani.panemgames.effects.ParticleEffect;

import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

public class AnimationManager {
		
	public static void playAnimationScoreboardBuildUp(PlayerScoreboardManager manager, Map<String, Integer> values) {
		AnimationScoreboardBuildUpThread thread = new AnimationScoreboardBuildUpThread(manager, values);
		thread.start();
	}
	
	public static void playAnimationLoading(Player p) {
		AnimationLoadingThread thread = new AnimationLoadingThread(p);
		thread.start();
	}
	
	public static void playAnimationCircle(Block b) {
		AnimationCircleThread thread = new AnimationCircleThread(b);
		thread.start();
	}
	
	public static class AnimationLoadingThread extends Thread {
		
		private Player p;
		private String[] loadingStatus = new String[] {
				"§8[   §aL§7OADING   §8]",
				"§8[   §7L§aO§7ADING   §8]",
				"§8[   §7LO§aA§7DING   §8]",
				"§8[   §7LOA§aD§7ING   §8]",
				"§8[   §7LOAD§aI§7NG   §8]",
				"§8[   §7LOADI§aN§7G   §8]",
				"§8[   §7LOADIN§aG   §8]"		
		};
		private boolean rightToLeft = false;
		
		public AnimationLoadingThread(Player p) {
			this.p = p;
		}
		
		public void run() {
			try {
				int i = 0;
				while(isAlive()) {
					if (i > -1 && i < 7)
						PlayerManager.send(loadingStatus[i], ChatPosition.ABOVE_ACTION_BAR, p);
					else
						rightToLeft = i < 0;				
					i = rightToLeft ? i + 1 : i - 1;
					sleep(100);
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}	
	}
	
	public static class AnimationCircleThread extends Thread {
		
		private Block b;
		
		public AnimationCircleThread(Block b) {
			this.b = b;
		}
		
		public void run() {
			try {
				double radius = 0.4;
				double increment = (2*Math.PI)/30;
	        	for(int i = 0; i < 30; i++){
	        		double angle = i*increment;
	        		double x = b.getX() + 0.5 + (radius * Math.cos(angle));
	        		double z = b.getZ() + 0.5 + (radius * Math.sin(angle));
	        		ParticleEffect.FIREWORKS_SPARK.display(0, 0, 0, 0, 1, new Location(b.getWorld(), x, b.getY() + 1, z), 10);
	        		sleep(50);
	        	}
			}
			catch (InterruptedException e) {
				e.printStackTrace();
			}
		}	
	}
	
	public static class AnimationScoreboardBuildUpThread extends Thread {
		
		private PlayerScoreboardManager manager;
		private Map<String, Integer> values;
		
		public AnimationScoreboardBuildUpThread(PlayerScoreboardManager manager, Map<String, Integer> values) {
			this.manager = manager;
			this.values = values;
		}
		
		public void run() {
			try {
				for (Entry<String, Integer> entry : values.entrySet()) {
					manager.addValueAll(entry.getKey(), entry.getValue());
					sleep(300);
				}
			}
			catch (InterruptedException e) {
				e.printStackTrace();
			}
		}	
	}

}
