package me.mani.panemgames.commands;

import me.mani.panemgames.LocationManager;
import me.mani.panemgames.PlayerManager;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class RemovePointCommand implements CommandExecutor {

	//Command: RemovePoint /removepoint
	//Descr.: Used to remove a location
		
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (!(sender instanceof Player))
			return false;
		Player p = (Player) sender;
					
		if (args.length == 1) {
			String name = args[0];
			if (LocationManager.getLocation(name) == null) {
				PlayerManager.send(PlayerManager.pre + " §cDiesen Punkt gibt es nicht!", p);
				return true;
			}
			LocationManager.remove(LocationManager.getLocation(name));
			PlayerManager.send(PlayerManager.pre + " §e" + name + " §8wurde entfernt!", p);
			return true;
		}
		if (args.length == 2) {
			if (args[0] != "spawn") {
				PlayerManager.send(PlayerManager.pre + " §eSyntax: /removepoint <SPAWN> <NUMBER>", p);
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
			if (LocationManager.getSpawnLocations(i) == null) {
				PlayerManager.send(PlayerManager.pre + " §cDiesen Spawn Punkt gibt es nicht!", p);
				return true;
			}
			LocationManager.remove(LocationManager.getSpawnLocations(i));
			PlayerManager.send(PlayerManager.pre + " §eSpawn " + i + " §8wurde enfernt!", p);
			return true;
		}
		PlayerManager.send(PlayerManager.pre + " §eSyntax: /removepoint <NAME / [SPAWN NUMBER]>", p);
		return true;
	}
	
}
