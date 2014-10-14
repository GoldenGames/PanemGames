package me.mani.panemgames;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.inventory.ItemStack;

public class ItemManager {
	
	private static List<ItemObject> allItemObjects = new ArrayList<>();
	
	public static void addItem(ItemObject itemObject) {
		if (getById(itemObject.getId()) == null)
			allItemObjects.add(itemObject);
		else
			System.out.println("An item with this id already exist!");
	}
	
	public static ItemObject getById(int id) {
		for (ItemObject itemObject : allItemObjects) {
			if (itemObject.getId() == id)
				return itemObject;
		}
		return null;
	}
	
	public static ItemObject getByItemStack(ItemStack itemStack) {
		for (ItemObject itemObject : allItemObjects) {
			if (itemStack.getType().equals(itemObject.getMaterial()))
				return itemObject;
			if (itemStack.getItemMeta().getDisplayName().equals(itemObject.getName()))
				return itemObject;
			if (itemStack.getItemMeta().getLore().equals(itemObject.getLore()))
				return itemObject;
		}
		return null;
	}
	
	public static List<ItemObject> getAll() {
		return allItemObjects;
	}

	public static void setAll(List<ItemObject> allItemObjects2) {
		for (ItemObject itemObject : allItemObjects2) {
			addItem(itemObject);
		}
	}
	
}
