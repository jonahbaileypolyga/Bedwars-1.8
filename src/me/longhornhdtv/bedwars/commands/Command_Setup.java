package me.longhornhdtv.bedwars.commands;

import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.util.HashSet;
import java.util.Set;

import org.bukkit.Material;
import org.bukkit.TravelAgent;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.material.Bed;

import com.avaje.ebeaninternal.server.persist.dml.GenerateDmlRequest;

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
			p.sendMessage("§e/setup createGame [maxPlayers] [Name des Spieles] erstelle den Server.");
			p.sendMessage("§e/setup setlobby setze den Lobby Spawn.");
			p.sendMessage("§e/setup setSpawnPosition [TeamFarbe  Schwartz,Weiß,Rot,Gelb,Grün,Lila,Orange,Blau] um den Teamspawn zu setzen.");
			p.sendMessage("§e/setup setBed [TeamFarbe] um das Bett zu setzen.");
			p.sendMessage("§e/setup setspawner [Bronze,Silver,Gold] um die Spawner zu setzen.");
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
				if(currentMap != null) {
					Teams teams = null;
					for(Teams tenum : Teams.values()) {
						if(tenum.name().equalsIgnoreCase(args[1].toUpperCase())) {
							teams = tenum;
						}
					}
					if(teams == null) {
						p.sendMessage("Du musst ein Name vo den Teams zwischen( Schwartz Weiß Rot Gelb Grün Lila Orange Blau) auswählen. ");
						return true;
					}
					Team team = currentMap.getTeam(teams);
					if(team == null) {
						p.sendMessage("Leider gibt es das angegebende Team(" + teams + ") nicht auf dieser Welt.");
						return true;
					}
					
					currentMap.removeTeam(team);
					
					//CODE from BedwarsRel
					HashSet<Material> transparent = new HashSet<Material>();
					transparent.add(Material.AIR);
					
					Class<?> hashsetType = getGenericTypeOfParameters(p.getClass(), "getTargetBlock", 0);
					Method targetBlockMethod = null;
					Block targetBlock = null;
					
					try {
						try {
							targetBlockMethod = p.getClass().getMethod("getTargetBlock", new Class<?>[]{Set.class, int.class});
						} catch (Exception ex) {
							try {
								targetBlockMethod = p.getClass().getMethod("getTargetBlock", new Class<?>[]{HashSet.class, int.class});
							} catch (Exception exc) {
								exc.printStackTrace();
							}
						}
						
						if(hashsetType.equals(Byte.class)) {
							targetBlock = (Block) targetBlockMethod.invoke(p, new Object[]{null, 15});
						}else{
							targetBlock = (Block) targetBlockMethod.invoke(p, new Object[]{transparent, 15});
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
					
					Block standingBlock = p.getLocation().getBlock().getRelative(BlockFace.DOWN);
					
					if(targetBlock == null || standingBlock == null) {
						p.sendMessage("Es gab ein Fehler beim speichern des Betts. Bitte gucke ob du genau über dem Bett bist und den vodere Teil anvisierst.");
						return true;
					}
					
					Material targetMaterial = Material.BED_BLOCK;
					
					Block theBlock = null;
					if(targetBlock.getType() == targetMaterial) {
						theBlock = targetBlock;
					}else{
						theBlock = standingBlock;
					}
					
					Block neightbor = null;
					Bed theBed = (Bed) theBlock.getState().getData();
					
					if(!theBed.isHeadOfBed()) {
						neightbor = theBlock;
						theBlock = getBedNeightbor(neightbor);
					}else{
						neightbor = getBedNeightbor(theBlock);
					}
					
					team.setBed(neightbor, theBlock);
					
					Main.game.removeMap(currentMap);
					
					currentMap.addTeam(team);
					
					Main.game.addMap(currentMap);
					p.sendMessage("Du hast erolgreich das Bett für Team(" + team.getTeamenum().getPrefix() + "§r) erstellt.");
					return true;
				}else{
					p.sendMessage("Du musst noch eine Karte hinzufügen auf der du bist oder ein Spiel erstellen.");
					return true;
				}
			}else{
				
				return true;
			}
		}else{
			
			return true;
		}
	}
	
	//UTILS
	public Class<?> getGenericTypeOfParameters(Class<?> clazz, String method, int parameterIndex) {
		try {
			Method m = clazz.getMethod(method, new Class<?>[]{Set.class, int.class});
			ParameterizedType type = (ParameterizedType) m.getGenericParameterTypes()[parameterIndex];
			return (Class<?>) type.getActualTypeArguments()[0];
		} catch (Exception e) {
			try {
				Method m = clazz.getMethod(method, new Class<?>[]{HashSet.class, int.class});
				ParameterizedType type = (ParameterizedType) m.getGenericParameterTypes()[parameterIndex];
				return (Class<?>) type.getActualTypeArguments()[0];
			} catch(Exception ex) {
				ex.printStackTrace();
			}
		}
		return null;
	}

	public static Block getBedNeightbor(Block head) {
		if(isBedBlock(head.getRelative(BlockFace.EAST))) {
			return head.getRelative(BlockFace.EAST);
		}else
		if(isBedBlock(head.getRelative(BlockFace.WEST))) {
			return head.getRelative(BlockFace.WEST);
		}else
		if(isBedBlock(head.getRelative(BlockFace.SOUTH))) {
			return head.getRelative(BlockFace.SOUTH);
		}else{
			return head.getRelative(BlockFace.NORTH);
		}
	}
	
	public static boolean isBedBlock(Block isBed) {
		if(isBed == null) {
			return false;
		}
		
		return (isBed.getType() == Material.BED || isBed.getType() == Material.BED_BLOCK);
	}
	
}
