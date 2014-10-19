package me.mani.panemgames;

import java.util.EnumMap;
import me.mani.panemgames.config.ConfigSession;
import org.bukkit.Material;

public class ItemManager {
	
	private static EnumMap<Material, Integer> materialDamage = new EnumMap<>(Material.class);
	
	public ItemManager() {
		ConfigSession session = new ConfigSession("damages");
		for (Material material : Material.values()) {
			session.getConfig().addDefault(material.name(), 0);
			if (session.getConfig().contains(material.name()))
				materialDamage.put(material, session.getConfig().getInt(material.name()));
		}
		session.save();
	}
	
	public static int getBaseDamage(Material material) {
		if (materialDamage.containsKey(material))
			return materialDamage.get(material);
		return 0;
	}
	
	public static boolean isCookable(Material material) {
		switch (material) {
		case PORK:
			return true;
		case RAW_FISH:
			return true;
		case RAW_CHICKEN:
			return true;
		case POTATO_ITEM:
			return true;
		case RAW_BEEF:
			return true;
		default:
			return false;
		}
	}
}
