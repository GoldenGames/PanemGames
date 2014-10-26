package me.mani.panemgames;

import java.util.Arrays;
import java.util.EnumMap;

import me.mani.panemgames.config.ConfigSession;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

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
	
	public static ItemStack createItemStack(Material mat, short data, int count) {
		return new ItemStack(mat, count, data);	 
	}
	
	public static ItemStack createItemStack(Material mat, short data, int count, String name) {
		ItemStack item = new ItemStack(mat, count, data);
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(name);
		item.setItemMeta(meta);
		return item;
	}
	
	public static ItemStack createItemStack(Material mat, short data, int count, String name, String... lore) {
		ItemStack item = new ItemStack(mat, count, data);
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(name);
		meta.setLore(Arrays.asList(lore));
		item.setItemMeta(meta);
		return item;
	}
	
	public static ItemStack addEnchantments(ItemStack item, CompactEnchantment... enchantments) {
		for (CompactEnchantment enchantment : enchantments)
			if (enchantment.getEnchantment().canEnchantItem(item))
				item.addEnchantment(enchantment.getEnchantment(), enchantment.getLevel());
			else
				item.addUnsafeEnchantment(enchantment.getEnchantment(), enchantment.getLevel());
		return item;
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
	
	public static class CompactEnchantment {
		
		private Enchantment enchantment;
		private int level;
		
		public CompactEnchantment(Enchantment enchantment, int level) {
			this.enchantment = enchantment;
			this.level = level;
		}
		
		public Enchantment getEnchantment() {
			return this.enchantment;
		}
		
		public int getLevel() {
			return this.level;
		}
		
	}
}
