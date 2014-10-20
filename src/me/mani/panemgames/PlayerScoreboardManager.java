package me.mani.panemgames;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;

public class PlayerScoreboardManager {

	private ScoreboardManager sm;
	
	private String title;
	private DisplaySlot slot;
	
	private List<Value> values = new ArrayList<>();
	
	public PlayerScoreboardManager(String title, DisplaySlot slot) {
		this.sm = Bukkit.getScoreboardManager();
		
		this.title = title;		
		this.slot = slot;
	}
	
	public Score addValueAll(String name, int id) {
		Score score = null;
		for (PlayerScoreboard playerScoreboard : allPlayerScoreboards) {
			score = playerScoreboard.getObjective().getScore(name);
			score.setScore(id);
		}	
		values.add(new Value(name, id));
		return score;
	}
	
	public void resetScoreAll(Score s) {
		for (PlayerScoreboard playerScoreboard : allPlayerScoreboards) {
			playerScoreboard.resetScore(s);
		}	
		values.remove(new Value(s.getEntry(), s.getScore()));
	}
	
	public void resetAllScoresAll() {
		for (PlayerScoreboard playerScoreboard : allPlayerScoreboards) {
			playerScoreboard.resetAllScores();
		}	
		values.clear();
	}
	
	public void addPlayerScoreboard(Player p) {
		PlayerScoreboard playerScoreboard = new PlayerScoreboard(p);
		playerScoreboard.setup(sm, title, slot, values);
		allPlayerScoreboards.add(playerScoreboard);
	}
	
	private static List<PlayerScoreboard> allPlayerScoreboards = new ArrayList<>();
	
	public static PlayerScoreboard getPlayerScoreboard(Player p) {
		for (PlayerScoreboard playerScoreboard : allPlayerScoreboards) {
			if (playerScoreboard.getPlayer().equals(p))
				return playerScoreboard;
		}
		return null;
	}
	
	public class PlayerScoreboard {
		
		private Player p;
		
		private Scoreboard s;	
		private Objective obj;	
		
		public PlayerScoreboard(Player p) {
			this.p = p;
		}
		
		public void setup(ScoreboardManager sm, String title, DisplaySlot slot, List<Value> values) {
			s = sm.getNewScoreboard();
			
			obj = s.registerNewObjective(title, "dummy");
			obj.setDisplaySlot(slot);
			
			for (Value v : values)
				this.addValue(v.getName(), v.getId());
		}
		
		public Player getPlayer() {
			return this.p;
		}
		
		public Scoreboard getScoreboard() {
			return this.s;
		}
		
		public Objective getObjective() {
			return this.obj;
		}

		public Score addValue(String name, int id) {
			Score s = obj.getScore(name);
			s.setScore(id);
			return s;
		}
		
		public void resetScore(Score s) {
			this.s.resetScores(s.getEntry());
		}	
		
		public void resetAllScores() {
			for (String entry : this.s.getEntries())
				this.s.resetScores(entry);
		}
	}
	
	private class Value {
		
		private String name;
		private int id;
		
		public Value(String name, int id) {
			this.name = name;
			this.id = id;
		}
		
		public String getName() {
			return this.name;
		}
		
		public int getId() {
			return this.id;
		}
		
	}
}
