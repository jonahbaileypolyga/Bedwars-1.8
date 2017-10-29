package me.longhornhdtv.bedwars.commands;

import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.material.Bed;
import org.bukkit.scheduler.BukkitRunnable;

import me.longhornhdtv.bedwars.main.Main;
import me.longhornhdtv.bedwars.utils.FakePlayer;
import me.longhornhdtv.bedwars.utils.Map;
import me.longhornhdtv.bedwars.utils.Spawner;
import me.longhornhdtv.bedwars.utils.SpawnerEnum;
import me.longhornhdtv.bedwars.utils.Team;
import me.longhornhdtv.bedwars.utils.Teams;

public class Command_Setup implements CommandExecutor{
	
	private Map currentMap = null;
	
	//setup setHub
	@SuppressWarnings("deprecation")
	//setup setSpawnPosition (Team)
	//setup setBed (Team)
	//setup setSpawner (Gold,Silver,Bronzer)
	//setup setLobby
	//setup addMap (Name) (VoteItemID:[MetaID])  - Permission: bw.addMap
	//setup createGame (Anzahl der Maximalen Spieler) (Name des Spiels) - Permission: bw.create
	//setup addTeam (TeamEnum) (maxmale Spieler für das Team) - Permission: bw.addTeam
	//setup setShop - Permission: bw.setShop
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
			p.sendMessage("§e/setup addMap [MapName] [VoteItem] um eine Map zu erstellen §c!Achte darauf das du dich in der Map befinden musst!§e. ");
			p.sendMessage("§e/setup setlobby setze den Lobby Spawn.");
			p.sendMessage("§e/setup setSpawnPosition [TeamFarbe  Schwartz,Weiß,Rot,Gelb,Grün,Lila,Orange,Blau] um den Teamspawn zu setzen.");
			p.sendMessage("§e/setup setBed [TeamFarbe] um das Bett zu setzen.");
			p.sendMessage("§e/setup setSpawner [Bronze,Silver,Gold] um die Spawner zu setzen.");
			p.sendMessage("");
			p.sendMessage("§e-------Setup-End-------");
			return true;
		}else
		if(args.length == 1) {
			if(args[0].equalsIgnoreCase("setLobby")) {
				if(p.getLocation().getBlock().getType() == Material.AIR){
					Main.game.lobbyLocation = p.getLocation().getBlock().getLocation().add(0.5D, 0.0D, 0.5D);
					p.sendMessage("Du hast erfolgreich die Lobby gesetzt.");
					return true;
				}else{
					p.sendMessage("Du kannst die Lobby nicht in der Luft setzten.");
					return true;
				}
			}else
			if(args[0].equalsIgnoreCase("setShop")){
				if(p.hasPermission("bw.setShop")) {
					Location loc = p.getLocation().getBlock().getLocation().add(0.5D, 0.0D, 0.5D);
					loc.setPitch(p.getLocation().getPitch());
					loc.setYaw(p.getLocation().getYaw());
					
					FakePlayer fp = new FakePlayer("§6ALDI", loc);
					
					if(currentMap != null) {
						fp.spawn();
						Bukkit.getScheduler().runTaskLater(Main.getPlugin(Main.class), new BukkitRunnable() {
							
							@Override
							public void run() {
								fp.rmvFromTablist();
								
							}
						}, 6);
						currentMap.addShopLocation(loc, fp);
					}else{
						p.sendMessage("Du musst davor die Map hinzufügen.");
						return true;
					}
					p.sendMessage("Der Shop wurde gesetzt.");
					return true;
				}else{
					
					return true;
				}
			}else
			if(args[0].equalsIgnoreCase("setSleep")) {
				if(currentMap != null) {
					for(Location loc : currentMap.getShopsLocations().keySet()) {
						FakePlayer fp = currentMap.getShopsLocations().get(loc);
						fp.destroy();
						fp.spawn();
						fp.sleep(true);
					}
					Bukkit.broadcastMessage("Leichen wurden gespawnt.:)");
					return true;
				}else{
					
					return true;
				}
			}else{
				
				return true;
			}
		}else
		if(args.length == 2){
			if(args[0].equalsIgnoreCase("setSpawner")) {
				if(currentMap != null) {
					SpawnerEnum se = null;
					for(SpawnerEnum sea : SpawnerEnum.values()) {
						if(sea.name().equalsIgnoreCase(args[1])) {
							se = sea;
						}
					}
					if(se == null) {
						p.sendMessage("Leider gibt es kein Spawner mit dem Name(" + args[1] + ").");
						return true;
					}
					Spawner s = new Spawner(se, p.getLocation().getBlock().getLocation().add(0.5D, 0.0D, 0.5D));
					s.setItem(new ItemStack(se.getMaterial()));
					s.generateSpawnerID();
					
					Main.game.removeMap(currentMap);
					
					currentMap.addSpawner(s);
					
					Main.game.addMap(currentMap);
				
					p.sendMessage("Du hast erfolgreich den Spawner(" + args[1].toUpperCase() + ") hinzugefügt.");
					return true;
				}else{
					p.sendMessage("§eDu musst noch eine Karte hinzufügen auf der du bist oder ein Spiel erstellen.");
					return true;
				}
			}else
			if(args[0].equalsIgnoreCase("setSpawnPosition")) {
				if(currentMap == null) {
					p.sendMessage("§eDu musst noch eine Karte hinzufügen auf der du bist oder ein Spiel erstellen.");
					return true;
				}
				Teams teams = null;
				for(Teams tenum : Teams.values()) {
					if(tenum.name().equalsIgnoreCase(args[1].toUpperCase())) {
						teams = tenum;
					}
				}
				
				if(teams == null) {
					p.sendMessage("§eDu musst ein Name von den Teams zwischen( Schwartz Weiß Rot Gelb Grün Lila Orange Blau) auswählen.");
					return true;
				}
				
				Team team = null;
				if(currentMap.getTeam(teams) == null) {
					p.sendMessage("Du musst zuerst das Team(" + teams.getPrefix() + ") erstellen.");
					return true;
				}
				
				team = currentMap.getTeam(teams);
				
				team.setSpawn(p.getLocation().getBlock().getLocation().add(0.5D, 0.0D, 0.5D));
				
				Main.game.removeMap(currentMap);
				
				if(currentMap.getTeams().contains(team)) {
					currentMap.removeTeam(team);
				}
				currentMap.addTeam(team);
					
				Main.game.addMap(currentMap);
				p.sendMessage("§eDu hast erolgreich den Team-Spawn für Team(§e" + team.getTeamenum().getPrefix() + "§e) erstellt.");
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
						p.sendMessage("§eDu musst ein Name von den Teams zwischen( Schwartz Weiß Rot Gelb Grün Lila Orange Blau) auswählen. Benutze /setup addTeam (Team) (Maximale Spieler Anzahl) um eins zu erstellen.");
						return true;
					}
					Team team = null;
					if(currentMap.getTeam(teams) == null) {
						p.sendMessage("§eLeider gibt es das angegebende Team(§e" + teams + "§e) nicht auf dieser Welt. Benutze /setup addTeam (Team) (Maximale Spieler Anzahl) um eins zu erstellen.");
						return true;
					}
					
					team = currentMap.getTeam(teams);
					
					if(currentMap.getTeams().contains(team)) {
						currentMap.removeTeam(team);
					}
					
//					currentMap.removeTeam(team);
						
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
						p.sendMessage("§eEs gab ein Fehler beim speichern des Betts. Bitte gucke ob du genau über dem Bett bist und den vodere Teil anvisierst.");
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
					p.sendMessage("§eDu hast erolgreich das Bett für Team(§e" + team.getTeamenum().getPrefix() + "§e) erstellt.");
					return true;
				}else{
					p.sendMessage("§eDu musst noch eine Karte hinzufügen auf der du bist oder ein Spiel erstellen.");
					return true;
				}
			}else{
				
				return true;
			}
		}else
		if(args.length == 3) {
			if(args[0].equalsIgnoreCase("addTeam")) {
				if(currentMap == null) {
					p.sendMessage("§eDu musst noch eine Karte hinzufügen auf der du bist oder ein Spiel erstellen.");
					return true;
				}
				if(!p.hasPermission("bw.addTeam")) {
					p.sendMessage("§7Du hast keine Rechte auf diesem Befehl§8!");
					return true;
				}
				Teams teams = null;
				for(Teams tenum : Teams.values()) {
					if(tenum.name().equalsIgnoreCase(args[1].toUpperCase())) {
						teams = tenum;
					}
				}
				
				if(teams == null) {
					p.sendMessage("§eDu musst ein Name von den Teams zwischen( Schwartz Weiß Rot Gelb Grün Lila Orange Blau) auswählen.");
					return true;
				}
				
				if(currentMap.getTeam(teams) == null) {
					int maxPlayers = 0;
					try {
						maxPlayers = Integer.parseInt(args[2]);
					} catch(Exception e) {
						p.sendMessage("Du musst eine Zahl eingeben bei Maximaler Spielranzahl für das Spiel.");
						return true;
					}
					
					if(maxPlayers == 0) {
						p.sendMessage("Du kannst dem Team nicht die Maximale Spieleranzahl auf 0 stellen.");
						return true;
					}
					Main.game.removeMap(currentMap);
					currentMap.addTeam(new Team(teams, maxPlayers));
					Main.game.addMap(currentMap);
					p.sendMessage("Das Team(" + teams.getPrefix() + ") wurde erfolgreich hinzugefügt.");
////					Map testmap = null;
//					if(!Main.game.isGameReady) {
//						for(Map maps : Main.game.getAllMaps()) {
//							if(maps.getMapName().equalsIgnoreCase(p.getLocation().getWorld().getName())) {
//								testmap = maps;
//								break;
//							}
//						}
//					}
//					p.sendMessage("DEBUG: Teams in Map: " + testmap.getTeams().toString() + " | Map: " + currentMap.getRealMapName() + " | Teams in currentMap: " + currentMap.getTeams().toString());
					return true;
				}else{
					p.sendMessage("Das Team(" + teams.getPrefix() + ") ist schon vorhanden auf dieser Map.");
					return true;
				}
				
			}else
			if(args[0].equalsIgnoreCase("addMap")) {
				if(!p.hasPermission("bw.addMap")) {
					p.sendMessage("§7Du hast keine Rechte auf diesem Befehl§8!");
					return true;
				}
				if(currentMap != null) {
					p.sendMessage("§eDiese Map wo du dich befindest wurde schon hinzugefügt.");
					return true;
				}
				String name = args[1];
				int voteItemID = 0;
				int metaID = 0;
				
				if(args[2].contains(":")) {
					try {
						String[] split = args[2].split(":");
						voteItemID = Integer.parseInt(split[0]);
						metaID = Integer.parseInt(split[1]);
					} catch(Exception e) {
						p.sendMessage("§eDu musst eine Zahl bei VoteItemID:MetaID eingeben.");
						return true;
					}
				}else{
					try {
						voteItemID = Integer.parseInt(args[2]);
					} catch(Exception e) {
						p.sendMessage("§eDu musst eine Zahl bei VoteItemID eingeben.");
						return true;
					}
				}
				if(Material.getMaterial(voteItemID) != null) { 
					Map map = new Map(name, p.getLocation().getWorld().getName());
					map.addVoteItem(new ItemStack(Material.getMaterial(voteItemID), 1, (short) metaID));
					Main.game.addMap(map);
					return true;
				}else{
					p.sendMessage("§eLeider gibt es kein Item mit der ID(" + voteItemID + ").");
					return true;
				}
			}
			if(args[0].equalsIgnoreCase("createGame")) {
				if(!p.hasPermission("bw.create")) {
					p.sendMessage("§7Du hast keine Rechte auf diesem Befehl§8!");
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
							p.sendMessage("§eDu musst eine Zahl bei der Maximalen Anzahl der Spieler für denn Server eingeben.");
							return true;
						}
						Main.game.setMaxPlayers(maxPlayers);
						Main.game.setGameName(name);
						p.sendMessage(Main.getPrefix() + "§eDu hast erfolgreich das Spiel mit dem Namen (" + name + ") erstellt!");
						return true;
					}else{
						if(!Main.GameFile.exists()) {
							name = args[2];
							try {
								maxPlayers = Integer.parseInt(args[1]);
							} catch(Exception e) {
								p.sendMessage("§eDu musst eine Zahl bei der Maximalen Anzahl der Spieler für denn Server eingeben.");
								return true;
							}
							Main.game.setMaxPlayers(maxPlayers);
							Main.game.setGameName(name);
							p.sendMessage(Main.getPrefix() + "§eDu hast erfolgreich das Spiel mit dem Namen (" + name + ") erstellt!");
							if(Main.isDebug) {
								Main.CCS.sendMessage(Main.getPrefix() + " §6DEBUG §0> §aStr(name): " + Main.game.getGameName() + " Int(maxPlayer): " + Main.game.getMaxPlayers()); 
							}
							return true;
						}else{
							p.sendMessage("§eEs gab ein Error: Command_Setup: 80|delete GameFile execption");
							return true;
						}
					}
				}else{
					p.sendMessage("§eDu hast auf diesem Server schon ein Spiel erstellt. Bitte erstelle Maps.");
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
