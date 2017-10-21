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
	//setup createGame (Anzahl der Maximalen Spieler) (Name des Spiels)
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String lebel, String[] args) {
		if(!(sender instanceof Player)) {
			
		}
		Player p = (Player) sender;
		if(!Main.game.isGameReady) {
			for(Map maps : Main.game.getAllMaps()) {
				if(maps.getMapName().equalsIgnoreCase(p.getLocation().getWorld().getName())) {
					currentMap = maps;
					break;
				}
			}
		}
		if(args.length == 0) {
			p.sendMessage("§e---------Setup---------");
			p.sendMessage("");
			p.sendMessage("§e/setup createGame [maxplayers] [Name des Spieles] erstelle den Server.");
			p.sendMessage("§e/setup setlobby setze den Lobby spawn.");
			p.sendMessage("§e/setup setSpawnPosition [TeamFarbe Gelb;Rot;Grün;Lila] um den Teamspawn zu setzen.");
			p.sendMessage("§e/setup setBed [TeamFarbe] um das Bett zu setzen.");
			p.sendMessage("§e/setup setspawner [Bronze;Silver;Gold] um die Spawner zu setzen.");
			p.sendMessage("");
			p.sendMessage("§e-------Setup-End-------");
			return true;
		}else
		if(args.length == 1) {
			if(args[0].equalsIgnoreCase("setLobby")) {
				Main.game.lobbyLocation = p.getLocation().getBlock().getLocation().add(0.5D, 0.0D, 0.5D);
				p.sendMessage("Du hast erfolgreich die Lobby gesetzt.");
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
			if(args[0].equalsIgnoreCase("createGame")) {
				if(!p.hasPermission("bw.create")) {
					p.sendMessage("Du hast keine Rechte auf diesem Befehl.");
					return true;
				}
				int maxPlayers = 0;
				String name = "";
				if(!Main.game.isGameReady) {
					if(Main.GameFile.delete()) {
						name = args[2];
						try {
							maxPlayers = Integer.parseInt(args[1]);
						} catch(Exception e) {
							p.sendMessage("Du musst eine Zahl bei der Maximalen Anzahl der Spieler für denn Server eingeben.");
							return true;
						}
						Main.game.setMaxPlayers(maxPlayers);
						Main.game.setGameName(name);
						p.sendMessage(Main.getPrefix() + "Du hast erfolgreich das Spiel mit dem Namen (" + name + ") erstellt!");
						return true;
					}else{
						if(!Main.GameFile.exists()) {
							name = args[2];
							try {
								maxPlayers = Integer.parseInt(args[1]);
							} catch(Exception e) {
								p.sendMessage("Du musst eine Zahl bei der Maximalen Anzahl der Spieler für denn Server eingeben.");
								return true;
							}
							Main.game.setMaxPlayers(maxPlayers);
							Main.game.setGameName(name);
							p.sendMessage(Main.getPrefix() + "Du hast erfolgreich das Spiel mit dem Namen (" + name + ") erstellt!");
							if(Main.isDebug) {
								Main.CCS.sendMessage(Main.getPrefix() + " §6DEBUG §0> §aStr(name): " + Main.game.getGameName() + " Int(maxPlayer): " + Main.game.getMaxPlayers()); 
							}
							return true;
						}else{
							p.sendMessage("Es gab ein Error: Command_Setup: 80|delete GameFile execption");
							return true;
						}
					}
				}else{
					p.sendMessage("Du hast auf diesem Server schon ein Spiel erstellt. Bitte erstelle Maps.");
					return true;
				}
			}else
			if(args[0].equalsIgnoreCase("setSpawnPosition")) {
				
				return true;
			}else
			if(args[0].equalsIgnoreCase("setBed")) {
				
				return true;
			}else{
				
				return true;
			}
		}else{
			
			return true;
		}
	}

}
