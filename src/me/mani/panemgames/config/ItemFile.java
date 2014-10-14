package me.mani.panemgames.config;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import me.mani.panemgames.ItemManager;
import me.mani.panemgames.ItemObject;

public class ItemFile extends ConfigFile {

	public ItemFile(String name) {
		super(name);
	}
	
	public void setAll() {
		List<Map<String, Object>> allItemObjects = new ArrayList<>();
		for (ItemObject itemObject : ItemManager.getAll()) {
			allItemObjects.add(itemObject.serialize());
		}
		getConfig().set("save", allItemObjects);
	}
	
	public void getAll() {
		List<ItemObject> allItemObjects = new ArrayList<>();
		for (Map<String, Object> map : (List<Map<String, Object>>) getConfig().getList("save")) {
			allItemObjects.add(ItemObject.deserialize(map));
		}
		ItemManager.setAll(allItemObjects);
	}

}
