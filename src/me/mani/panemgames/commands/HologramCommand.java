package me.mani.panemgames.commands;

import me.mani.panemgames.holograms.Hologram;
import net.md_5.bungee.api.ChatColor;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class HologramCommand implements CommandExecutor {

	private String pre = "§7[§ePanemHolograms§7]";
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label,
			String[] args) {
		if (!(sender instanceof Player))
			return false;
		Player p = (Player) sender;

		if (args.length >= 1) {
			
			// Hologram Help: /hologram help
			
			if (args[0].equalsIgnoreCase("help")) {
				for (CommandType type : CommandType.values())
					p.sendMessage(pre + " §8" + type.getSyntax());
			} 
			
			// Hologram Create: /hologram create <ID>
			
			else if (args[0].equalsIgnoreCase("create")) {
				if (args.length != 2) {
					sendSyntax(p, CommandType.CREATE);
					return true;
				}
				if (Hologram.getHologram(args[1]) != null) {
					sendError(p, "Es gibt schon eine Hologramm mit diesem Namen");
					return true;
				}
				Hologram holo = new Hologram(args[1], p.getLocation());
				holo.addLine("§3Hologram: §6" + holo.getId());
				holo.addLine("§eExample line");
				holo.addLine("§7Edit with: §c/holo setline " + holo.getId() + " <Linien-ID> <Text>");
			} 
			
			// Hologram AddLine: /hologram addline <Hologram-ID> <Text>
			
			else if (args[0].equalsIgnoreCase("addline")) {
				if (args.length < 3) {
					sendSyntax(p, CommandType.ADDLINE);
					return true;
				}
				if (Hologram.getHologram(args[1]) == null) {
					sendError(p, "Es gibt kein Hologramm mit dieser ID");
					return true;
				}
				Hologram holo = Hologram.getHologram(args[1]);
				holo.addLine(toText(args, 2));
				return true;
			} 
			
			// Hologram AddLineAt: /hologram addlineat <Hologram-ID> <Linien-ID> <Text>
			
			else if (args[0].equalsIgnoreCase("addlineat")) {
				if (args.length < 4) {
					sendSyntax(p, CommandType.ADDLINEAT);
					return true;
				}
				if (Hologram.getHologram(args[1]) == null) {
					sendError(p, "Es gibt kein Hologramm mit dieser ID");
					return true;
				}
				Hologram holo = Hologram.getHologram(args[1]);	
				try {
					if (holo.getLine(Integer.valueOf(args[2])) == null) {
						sendError(p, "Es keine Linie mit dieser ID");
						return true;
					}
				} catch (NumberFormatException e) {
					sendError(p, "Die Linien-ID muss eine Zahl sein");
					return true;
				}
				holo.addLine(Integer.valueOf(args[2]), toText(args, 3));
				return true;
			} 
			
			// Hologram SetLine: /hologram setline <Hologram-ID> <Linien-ID> <Text>
			
			else if (args[0].equalsIgnoreCase("setline")) {
				if (args.length < 4) {
					sendSyntax(p, CommandType.SETLINE);
					return true;
				}
				if (Hologram.getHologram(args[1]) == null) {
					sendError(p, "Es gibt kein Hologramm mit dieser ID");
					return true;
				}
				Hologram holo = Hologram.getHologram(args[1]);
				try {
					if (holo.getLine(Integer.valueOf(args[2])) == null) {
						sendError(p, "Es keine Linie mit dieser ID");
						return true;
					}
				} catch (NumberFormatException e) {
					sendError(p, "Die Linien-ID muss eine Zahl sein");
					return true;
				}
				holo.setLine(Integer.valueOf(args[2]), toText(args, 3));
				return true;
			}
			
			// Hologram Remove: /hologram remove <Hologram-ID>
			
			else if (args[0].equalsIgnoreCase("remove")) {
				if (args.length != 2) {
					sendSyntax(p, CommandType.REMOVE);
					return true;
				}
				if (Hologram.getHologram(args[1]) == null) {
					sendError(p, "Es gibt kein Hologramm mit dieser ID");
					return true;
				}
				Hologram holo = Hologram.getHologram(args[1]);
				holo.remove();
			}
			
			// Hologram RemoveLine: /hologram removeline <Hologram-ID> <Linien-ID>
			
			else if (args[0].equalsIgnoreCase("removeline")) {
				if (args.length != 3) {
					sendSyntax(p, CommandType.REMOVELINE);
					return true;
				}
				if (Hologram.getHologram(args[1]) == null) {
					sendError(p, "Es gibt kein Hologramm mit dieser ID");
					return true;
				}
				Hologram holo = Hologram.getHologram(args[1]);
				try {
					if (holo.getLine(Integer.valueOf(args[2])) == null) {
						sendError(p, "Es keine Linie mit dieser ID");
						return true;
					}
				} catch (NumberFormatException e) {
					sendError(p, "Die Linien-ID muss eine Zahl sein");
					return true;
				}				
				holo.removeLine(Integer.valueOf(args[2]));
			}
			
			// Hologram RemoveRaw: /hologram removeraw
			
			else if (args[0].equalsIgnoreCase("removeraw")) {
				Hologram.removeRaw(p);
			}	
			
			// Hologram Info: /hologram info <Hologram-ID>
			
			else if (args[0].equalsIgnoreCase("info")) {
				if (args.length != 2) {
					sendSyntax(p, CommandType.INFO);
					return true;
				}
				if (Hologram.getHologram(args[1]) == null) {
					sendError(p, "Es gibt kein Hologramm mit dieser ID");
					return true;
				}
				Hologram holo = Hologram.getHologram(args[1]);
				p.sendMessage((String[]) holo.getLineTexts().toArray());
			}
		}

		return true;
	}
	
	private String toText(String[] args, int startingIndex) {
		StringBuilder sb = new StringBuilder();
		for (int i = startingIndex; i < args.length; i++) {
			sb.append(args[i] + " ");
		}
		return ChatColor.translateAlternateColorCodes('&', sb.toString().trim());
	}
	
	private void sendSyntax(Player p, CommandType type) {
		p.sendMessage(this.pre + " §8Syntax: §e" + type.getSyntax());
	}
	
	private void sendError(Player p, String errorText) {
		p.sendMessage(this.pre + " §8Error: §c" + errorText);
	}
	
	private enum CommandType {
		HELP ("/hologram help"), 
		CREATE ("/hologram create <ID>"), 
		ADDLINE ("/hologram addline <Hologram-ID> <Text>"), 
		ADDLINEAT ("/hologram addline <Hologram-ID> <Linien-ID> <Text>"),
		SETLINE ("/hologram setline <Hologram-ID> <Linien-ID> <Text>"), 
		REMOVELINE ("/hologram removeline <Hologram-ID> <Linien-ID>"), 
		REMOVE ("/hologram remove <Hologram-ID>"), 
		REMOVERAW ("/hologram removeraw"),
		INFO ("/hologram info <Hologram-ID>");
		
		private CommandType(String syntax) {
			this.syntax = syntax;
		}
		
		private String syntax;
		
		public String getSyntax() {
			return this.syntax;
		}
	}

}
