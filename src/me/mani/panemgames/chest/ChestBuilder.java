package me.mani.panemgames.chest;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;

import me.mani.panemgames.ItemManager;
import me.mani.panemgames.chest.ChestManager.ChestType;

import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class ChestBuilder {

	private PanemChest chest;
	private ChestType type;
	
	private static EnumMap<ItemQuality, List<ItemStack>> allItems = new EnumMap<>(ItemQuality.class);
	
	public ChestBuilder(PanemChest chest, ChestType type) {
		this.chest = chest;
		this.type = type;
	}
	
	public void build() {
		Inventory inv = chest.getChestBlock().getBlockInventory();
		for (int i : getSlotNumbers(type == ChestType.SPAWN_CHEST ? getRandomInteger(1, 2) : getRandomInteger(2, 5))) {
			ItemStack randomItem = getRandomItemStack();
			
			if (type == ChestType.SPAWN_CHEST && inv.contains(randomItem))
				while (inv.contains(randomItem))
					randomItem = getRandomItemStack();
			
			inv.setItem(i, getRandomItemStack());
		}
	}
	
	private int getRandomInteger(int min, int max) {
		return min + (int) (Math.random() * ((max - min) + 1));
	}
	
	private Integer[] getSlotNumbers(int itemCount) {
		if (type == ChestType.SPAWN_CHEST) {
			switch (itemCount) {
			case 1:
				return new Integer[] {13};
			case 2:
				return new Integer[] {11, 15};
			case 3:
				return new Integer[] {10, 13, 16};
			case 4:
				return new Integer[] {10, 12, 14, 16};
			case 5:
				return new Integer[] {9, 11, 13, 15, 17};
			default:
				return new Integer[] {};
			}
		}
		else if (type == ChestType.WILD_CHEST) {
			List<Integer> allSlotNumbers = new ArrayList<>();
			for (int i = 1; i <= itemCount; i++) {
				int randomSlotNumber = getRandomInteger(0, 26);
				while (allSlotNumbers.contains(randomSlotNumber))
					randomSlotNumber = getRandomInteger(0, 26);
				allSlotNumbers.add(randomSlotNumber);
			}
			return allSlotNumbers.toArray(new Integer[allSlotNumbers.size()]);
		}
		return new Integer[] {};
	}
	
	private ItemStack getRandomItemStack() {
		return getRandomItemStack(getRandomItemQuality());
	}
	
	private ItemStack getRandomItemStack(ItemQuality quality) {
		return allItems.get(quality).get(getRandomInteger(0, allItems.get(quality).size() - 1));
	}
	
	private ItemQuality getRandomItemQuality() {	
		if (type.equals(ChestType.SPAWN_CHEST))
			return ItemQuality.SPAWN_ONLY;
		
		int randomChance = getRandomInteger(0, 100);
		
		if (randomChance < 5)
			return ItemQuality.HIGH;
		else if (randomChance < 20)
			return ItemQuality.SEMI_HIGH;
		else if (randomChance < 50)
			return ItemQuality.SEMI_LOW;
		return ItemQuality.LOW;
	}
	
	private enum ItemQuality {
		SPAWN_ONLY, HIGH, SEMI_HIGH, SEMI_LOW, LOW;
	}
	
	static {
		for (ItemQuality quality : ItemQuality.values())
			allItems.put(quality, new ArrayList<ItemStack>());
		
		allItems.get(ItemQuality.SPAWN_ONLY).add(ItemManager.createItemStack(Material.CHAINMAIL_HELMET, (short) 0, 1));
		allItems.get(ItemQuality.SPAWN_ONLY).add(ItemManager.createItemStack(Material.CHAINMAIL_CHESTPLATE, (short) 0, 1));
		allItems.get(ItemQuality.SPAWN_ONLY).add(ItemManager.createItemStack(Material.CHAINMAIL_LEGGINGS, (short) 0, 1));
		allItems.get(ItemQuality.SPAWN_ONLY).add(ItemManager.createItemStack(Material.CHAINMAIL_BOOTS, (short) 0, 1));
		allItems.get(ItemQuality.SPAWN_ONLY).add(ItemManager.createItemStack(Material.IRON_AXE, (short) 0, 1));
		allItems.get(ItemQuality.SPAWN_ONLY).add(ItemManager.createItemStack(Material.IRON_SWORD, (short) 0, 1));
		allItems.get(ItemQuality.SPAWN_ONLY).add(ItemManager.createItemStack(Material.BOW, (short) 0, 1));
		
		allItems.get(ItemQuality.HIGH).add(ItemManager.createItemStack(Material.RAW_BEEF, (short) 0, 1));
		allItems.get(ItemQuality.HIGH).add(ItemManager.createItemStack(Material.PORK, (short) 0, 1));
		allItems.get(ItemQuality.HIGH).add(ItemManager.createItemStack(Material.STONE_SWORD, (short) 0, 1));
		allItems.get(ItemQuality.HIGH).add(ItemManager.createItemStack(Material.IRON_INGOT, (short) 0, 1));
		allItems.get(ItemQuality.HIGH).add(ItemManager.createItemStack(Material.CHEST, (short) 0, 1));
		
		allItems.get(ItemQuality.SEMI_HIGH).add(ItemManager.createItemStack(Material.BOWL, (short) 0, 1));
		allItems.get(ItemQuality.SEMI_HIGH).add(ItemManager.createItemStack(Material.STONE_AXE, (short) 0, 1));
		allItems.get(ItemQuality.SEMI_HIGH).add(ItemManager.createItemStack(Material.WOOD_SWORD, (short) 0, 1));
		allItems.get(ItemQuality.SEMI_HIGH).add(ItemManager.createItemStack(Material.FIREBALL, (short) 0, 1));
		allItems.get(ItemQuality.SEMI_HIGH).add(ItemManager.createItemStack(Material.TORCH, (short) 0, 1));		
		allItems.get(ItemQuality.SEMI_HIGH).add(ItemManager.createItemStack(Material.ARROW, (short) 0, 1));
		
		allItems.get(ItemQuality.SEMI_LOW).add(ItemManager.createItemStack(Material.CARROT_ITEM, (short) 0, 1));
		allItems.get(ItemQuality.SEMI_LOW).add(ItemManager.createItemStack(Material.APPLE, (short) 0, 1));
		allItems.get(ItemQuality.SEMI_LOW).add(ItemManager.createItemStack(Material.POTATO_ITEM, (short) 0, 1));
		allItems.get(ItemQuality.SEMI_LOW).add(ItemManager.createItemStack(Material.WOOD_AXE, (short) 0, 1));
		allItems.get(ItemQuality.SEMI_LOW).add(ItemManager.createItemStack(Material.FISHING_ROD, (short) 0, 1));
		allItems.get(ItemQuality.SEMI_LOW).add(ItemManager.createItemStack(Material.WOOD_SPADE, (short) 0, 1));
		allItems.get(ItemQuality.SEMI_LOW).add(ItemManager.createItemStack(Material.WHEAT, (short) 0, 1));
		allItems.get(ItemQuality.SEMI_LOW).add(ItemManager.createItemStack(Material.LEATHER, (short) 0, 1));
		allItems.get(ItemQuality.SEMI_LOW).add(ItemManager.createItemStack(Material.BONE, (short) 0, 1));
		allItems.get(ItemQuality.SEMI_LOW).add(ItemManager.createItemStack(Material.FLINT, (short) 0, 1));
		
		allItems.get(ItemQuality.LOW).add(ItemManager.createItemStack(Material.RAW_CHICKEN, (short) 0, 1));
		allItems.get(ItemQuality.LOW).add(ItemManager.createItemStack(Material.POISONOUS_POTATO, (short) 0, 1));
		allItems.get(ItemQuality.LOW).add(ItemManager.createItemStack(Material.STICK, (short) 0, 2));
		allItems.get(ItemQuality.LOW).add(ItemManager.createItemStack(Material.GLASS_BOTTLE, (short) 0, 1));
		allItems.get(ItemQuality.LOW).add(ItemManager.createItemStack(Material.FEATHER, (short) 0, 1));
		allItems.get(ItemQuality.LOW).add(ItemManager.createItemStack(Material.STRING, (short) 0, 1));
	}
	
}
