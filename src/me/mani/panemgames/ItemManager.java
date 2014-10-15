package me.mani.panemgames;

import java.util.EnumMap;
import me.mani.panemgames.config.ConfigSession;
import org.bukkit.Material;

public class ItemManager {
	
	private static EnumMap<Material, Integer> materialDamage = new EnumMap<>(Material.class);
	
	static {
		ConfigSession session = new ConfigSession("damages");
		for (Material material : Material.values()) {
			session.getConfig().addDefault(material.name(), 0);
			if (session.getConfig().contains(material.name()))
				materialDamage.put(material, session.getConfig().getInt(material.name()));
		}
	}
	
	public static int getBaseDamage(Material material) {
		if (materialDamage.containsKey(material))
			return materialDamage.get(material);
		return 0;
	}
}
