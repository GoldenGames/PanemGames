package me.mani.panemgames;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.Material;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.util.NumberConversions;

public class ItemObject implements ConfigurationSerializable {
	
	private int id;
	private Material material;
	private String name;
	private List<String> lore;
	private int damage;
	
	public ItemObject(int id, Material material, String name, List<String> lore, int damage) {
		this.id = id;
		this.material = material;
		this.name = name;
		this.lore = lore;
		this.damage = damage;
	}
	
	public ItemStack toItemStack() {
		ItemStack item = new ItemStack(getMaterial());
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(getName());
		meta.setLore(lore);
		item.setItemMeta(meta);
		return item;
	}
	
	public int getId() {
		return this.id;
	}
	
	public int getDamage() {
		return this.damage;
	}

	public Material getMaterial() {
		return material;
	}

	public String getName() {
		return name;
	}
	
	public List<String> getLore() {
		return lore;
	}

	public boolean equals(ItemStack itemStack) {
		if (itemStack.getType().equals(getMaterial()))
			return true;
		if (itemStack.getItemMeta().getDisplayName().equals(getName()))
			return true;
		if (itemStack.getItemMeta().getLore().equals(lore))
			return true;
		return false;
	}

	@Override
	public Map<String, Object> serialize() {
		HashMap<String, Object> map = new HashMap<>();
		map.put("id", id);
		map.put("material", getMaterial().name());
		map.put("name", getName());
		map.put("lore", lore);
		map.put("damage", damage);
		return map;
	}
	
	@SuppressWarnings("unchecked")
	public static ItemObject deserialize(Map<String, Object> saveMap) {
		return new ItemObject(
			NumberConversions.toInt(saveMap.get("id")),
			Material.valueOf(String.valueOf(saveMap.get("material"))),
			String.valueOf(saveMap.get("name")),
			(List<String>) saveMap.get("lore"),
			NumberConversions.toInt(saveMap.get("damage"))
		);
	}
	
}