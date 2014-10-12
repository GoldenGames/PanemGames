package me.mani.panemgames.holograms;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.WitherSkull;

public class Hologram {
	
	private String id;
	private Location loc;
	
	private List<HologramLine> hologramLines = new ArrayList<>();
	
	public Hologram(String id, Location loc) {
		this.id = id;
		this.loc = loc;
		
		allHolograms.add(this);
	}
	
	public void update() {
		int i = 0;
		for (HologramLine line : hologramLines) {
			line.move(this.loc.getY() + 56 - i*0.3);
			i++;
		}
	}
	
	public String getId() {
		return this.id;
	}
	
	public void addLine(String text) {
		hologramLines.add(new HologramLine(text, this.loc));
		update();
	}
	
	public void addLine(int id, String text) {
		hologramLines.add(id, new HologramLine(text, this.loc));
		update();
	}	
	
	public boolean hasLines() {
		return !hologramLines.isEmpty();
	}
	
	public HologramLine getLine(int id) {
		return hologramLines.get(id);
	}
	
	public List<String> getLineTexts() {
		List<String> lineTexts = new ArrayList<>();
		for (HologramLine line : hologramLines)
			lineTexts.add(line.getText());
		return lineTexts;
	}
	
	public void setLine(int id, String newText) {
		hologramLines.get(id).setText(newText);
	}
	
	public void removeLine(int id) {
		hologramLines.get(id).remove();
		hologramLines.remove(id);
		if (hasLines())
			update();
		else
			remove();
	}
	
	public void removeAllLines() {
		for (HologramLine hl : hologramLines) {
			hl.remove();
		}
		hologramLines.removeAll(hologramLines);
	}
	
	public void remove() {
		if (hasLines())
			removeAllLines();
		allHolograms.remove(this);
	}
	
	private static List<Hologram> allHolograms = new ArrayList<>();

	public static Hologram getHologram(String id) {
		for (Hologram h : allHolograms) {
			if (h.getId().equals(id))
				return h;
		}
		return null;
	}
	
	public static void removeRaw(Player p) {
		for(Entity e : p.getNearbyEntities(0.5, 1000, 0.5)) {
			if (e instanceof WitherSkull)
				e.remove();
		}
	}

}
