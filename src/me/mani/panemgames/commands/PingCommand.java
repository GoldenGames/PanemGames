package me.mani.panemgames.commands;

import me.mani.panemgames.PlayerManager;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class PingCommand implements CommandExecutor {
	
	//Command: Ping /ping /p
	//Descr.: Used to get the own ping
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (!(sender instanceof Player))
			return false;
		Player p = (Player) sender;
		
		PlayerManager.send(PlayerManager.pre + " §7Ping: §e" + PlayerManager.getPing(p) + " ms", p);
		
		return true;
	}

}
