package me.longhornhdtv.bedwars.commands;

import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.longhornhdtv.bedwars.main.Main;
import me.longhornhdtv.bedwars.utils.Map;
import me.longhornhdtv.bedwars.utils.Team;
import me.longhornhdtv.bedwars.utils.Teams;

public class Command_Setup implements CommandExecutor{
	
	private Map currentMap = null;
	
	//setup setHub
	//setup setSpawnPosition (Team)
	//setup setBed (Team)
	//setup setSpawner (Gold,Silver,Bronzer)
	//setup setLobby
	//setup createGame (Anzahl der Maximalen Spieler) (Anzahl der Teams) (VoteItemID:[MetaID]) 
	@SuppressWarnings("deprecation")
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String lebel, String[] args) {
		if(!(sender instanceof Player)) {
			
		}
		Player p = (Player) sender;
		for(Map maps : Main.game.getAllMaps()) {
			if(maps.getMapName().equalsIgnoreCase(p.getLocation().getWorld().getName())) {
				currentMap = maps;
				break;
			}
		}
		if(args.length == 0) {
			if(args[0].equalsIgnoreCase("setLobby")) {
				
				return true;
			}else
			if(args[0].equalsIgnoreCase("setHub")){
				
				return true;
			}else{
				
				return true;
			}
			
		}else
		if(args.length == 1) {
			if(args[0].equalsIgnoreCase("setHub")) {
				
				return true;
			}else{
				
				return true;
			}
		}else
		if(args.length == 2){
			if(args[0].equalsIgnoreCase("setSpawner")) {
				
				return true;
			}else{
				
				return true;
			}
		}else
		if(args.length == 3) {
			if(args[0].equalsIgnoreCase("setSpawnPosition")) {
				
				return true;
			}else
			if(args[0].equalsIgnoreCase("setBed")) {
				
				return true;
			}else{
				
				return true;
			}
		}else
		if(args.length == 4) {
			if(args[0].equalsIgnoreCase("createGame")) {
				int countofmaxplayers = 0;
				int countofteams = 0;
				int voteitemid = 0;
				int metaid = 0;
				
				try {
					countofmaxplayers = Integer.getInteger(args[1]);
					countofteams = Integer.getInteger(args[2]);
				} catch(Exception e) {
					p.sendMessage("§eBitte gebe die Zahlen ein bei (Anzahl der Maximalen Spieler) ,(Anzahl der Teams) und (VoteItemID:[MetaID]).");
					return true;
				}
				if(args[3].contains(":")) {
					try {
						voteitemid = Integer.getInteger(args[3]);
					} catch(Exception e) {
						p.sendMessage("§eBitte gebe die Zahlen ein bei (Anzahl der Maximalen Spieler) ,(Anzahl der Teams) und (VoteItemID:[MetaID]).");
						return true;
					}
				}else{
					String[] split = args[3].split(":");
					try {
						voteitemid = Integer.getInteger(split[0]);
						metaid = Integer.getInteger(split[1]);
					} catch(Exception e) {
						p.sendMessage("§eBitte gebe die Zahlen ein bei (Anzahl der Maximalen Spieler) ,(Anzahl der Teams) und (VoteItemID:[MetaID]).");
						return true;
					}
				}
				if(countofmaxplayers == 0 || countofteams== 0) {
					p.sendMessage("&eDu kannst §ckein Spiel §emit einer Anzahl von 0 Teams oder 0 Maximalen Spieler erstellen.");
					return true;
				}
				
				if(Material.getMaterial(voteitemid) != null) {
					if(currentMap != null) {
						p.sendMessage("§eAuf dieser Welt gibt es schon ein Game.");
						return true;
					}
					
					
					if(countofmaxplayers > 16) {
						p.sendMessage("§eDu kannst nicht mehr als 16 Unterschiedliche Teams erstellen.");
						return true;
					}
					
				}else{
					p.sendMessage("§eEs gibt kein Item mit der ID §e" + voteitemid + "$e in Minecraft.");
					return true;
				}
				return true;
			}else{
				
				return true;
			}
		}else{
			
			return true;
		}
	}

}
