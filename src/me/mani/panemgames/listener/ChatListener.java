package me.mani.panemgames.listener;

import me.mani.panemgames.PanemGames;
import me.mani.panemgames.PlayerManager;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.HoverEvent.Action;
import net.md_5.bungee.api.chat.TextComponent;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class ChatListener implements Listener {
	
	private PanemGames pl;
	
	public ChatListener(PanemGames pl) {
		this.pl = pl;
	}
	
	@EventHandler
	public void onChat(AsyncPlayerChatEvent ev) {
		Player p = ev.getPlayer();
		String message = ev.getMessage();
		ev.setCancelled(true);
		for (Player player : PlayerManager.getAll()) {
			TextComponent text = new TextComponent("§4" + p.getName() + " §8| §c" + message);
			text.setHoverEvent(new HoverEvent(Action.SHOW_TEXT, new ComponentBuilder("§7[§cAdmin§7]").create()));
			player.spigot().sendMessage(text);
		}
	}

}
