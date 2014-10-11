package me.mani.panemgames.commands;

import me.mani.panemgames.GameLocation;
import me.mani.panemgames.LocationManager;
import me.mani.panemgames.PlayerManager;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SetPointCommand implements CommandExecutor {

	//Command: SetPoint /setpoint
	//Descr.: Used to add a location
	//Args: <spawn> to add a spawn location
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (!(sender instanceof Player))
			return false;
		Player p = (Player) sender;
				
		if (args.length == 1) {
			String name = args[0];
			LocationManager.add(new GameLocation(name, p.getLocation()));
			PlayerManager.send(PlayerManager.pre + " §e" + name + " §8wurde hinzugefügt!", p);
			return true;
		}
		if (args.length == 2) {
			if (args[0] != "spawn") {
				PlayerManager.send(PlayerManager.pre + " §eSyntax: /setpoint <SPAWN> <NUMBER>", p);
				return true;
			}
			int i = -1;
			try {
				i = Integer.valueOf(args[1]);
			}
			catch (NumberFormatException e) {
				PlayerManager.send(PlayerManager.pre + " §cDas zweite Argument muss eine Zahl sein", p);
				return true;
			}
			LocationManager.add(new GameLocation("spawn" + i, p.getLocation()));
			PlayerManager.send(PlayerManager.pre + " §eSpawn " + i + " §8wurde hinzugefügt!", p);
			return true;
		}
		PlayerManager.send(PlayerManager.pre + " §eSyntax: /setpoint <NAME / [SPAWN NUMBER]>", p);
		return true;
	}

}
