package me.longhornhdtv.bedwars.utils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import me.longhornhdtv.bedwars.main.Main;

public class Game {
	//TODO: Maps müssen noch gespeichert werden!!!
	//TODO: Map-VoteSystem programmieren.
	//TODO: In Maps müssen noch die Team, also Teams Programmieren!!!
	//TODO: Shop: Alter und Neuer Shop programmieren.
	//TODO: FakePlayer Programmieren.
	//TODO: Fertig!!!
		
	private String name;
	private int maxPlayers;
	private Map currentMap;
	public boolean isGameReady;
	public boolean isBuildMode;
	public boolean isDebug = true;
	public Location lobbyLocation;
	private File file;
	private GameState gameState;
	
	private ArrayList<Map> maps = new ArrayList<>();
	
	public Game(File file) {
		if(!isDebug) {
			if(!file.exists()) {
				isGameReady = false;
				isBuildMode = false;
			}else{
//				this.isGameReady = true;
				this.file = file;
				YamlConfiguration cfg = YamlConfiguration.loadConfiguration(file);
				this.isBuildMode = cfg.getBoolean("Game.BuildMode");
				this.name = cfg.getString("Game.Name");
				this.maxPlayers = cfg.getInt("Game.MaxPlayers");
				this.lobbyLocation = new Location(Bukkit.getWorld(cfg.getString("Game.Lobby.World")), cfg.getDouble("Game.Lobby.X"), cfg.getDouble("Game.Lobby.Y"), cfg.getDouble("Game.Lobby.Z"));
				for(String name : cfg.getConfigurationSection("Game.Maps").getKeys(false)) {
					String unrealName = cfg.getString("Game.Maps" + name + ".Name");
					boolean b1 = false;
					Map map = new Map(unrealName, name);
					for(Map map2 : this.maps) {
						if(map2.getMapName().equals(map)) {
							b1 = true;
							break;
						}
					}
					
					if(b1) {
						b1 = false;
						continue;
					}else{
						maps.add(map);
						b1 = false;
						continue;
					}
				}
				boolean b2 = true;
				if(this.maps.size() < 3) {
					b2 = false;
				}
				
				while(this.maps.size() > 3 & b2) {
					Random rnd = new Random();
					Map removeMap = this.maps.get(rnd.nextInt(this.maps.size()));
					Main.unloadWold(removeMap.getRealMapName());
					this.maps.remove(removeMap);
				}
				for(Map map : this.maps) {
					Main.loadWorld(map.getRealMapName());
					ItemStack voteItem = cfg.getItemStack("Game.Maps." + map.getMapName() + ".VoteItem");
					ArrayList<Spawner> spawners = new ArrayList<>();
					for(String SpawnerID : cfg.getConfigurationSection("Game.Maps." + map.getMapName() + ".Spawners").getKeys(false)) {
						Spawner spawner;
						
						SpawnerEnum spawnerEnum = SpawnerEnum.valueOf(cfg.getString("Game.Maps." + map.getMapName() + ".Spawners." + SpawnerID + ".SpawnerEnum"));
						ItemStack item = cfg.getItemStack("Game.Maps." + map.getMapName() + ".Spawners." + SpawnerID + ".SpawnerEnum");
						Location loc = new Location(Bukkit.getWorld(cfg.getString("Game.Maps." + map.getMapName() + ".Spawners." + SpawnerID + ".Location.World")),
								cfg.getDouble("Game.Maps." + map.getMapName() + ".Spawners." + SpawnerID + ".Location.X"), 
								cfg.getDouble("Game.Maps." + map.getMapName() + ".Spawners." + SpawnerID + ".Location.Y"), 
								cfg.getDouble("Game.Maps." + map.getMapName() + ".Spawners." + SpawnerID + ".Location.Z"));
						spawner = new Spawner(spawnerEnum, loc);
						spawner.setSpawnerID(UUID.fromString(SpawnerID));
						spawner.setItem(item);
						spawners.add(spawner);
					}
				}
				gameState = GameState.LOBBY;
			}
		}
	}
	
	
	public void addMap(Map map) {
		if(!maps.contains(map) || maps.isEmpty()) {
			if(Bukkit.getWorld(map.getRealMapName()) != null) {
				Map map2 = new Map(map.getMapName(), Bukkit.getWorld(map.getRealMapName()).getName());
//				for(Spawner spawner : map.getAllSpawnerfromtheMap()) {
//					map2.addSpawner(spawner);
//				}
//				if(map2.getAllSpawnerfromtheMap() == null) {
//					Bukkit.broadcastMessage(Main.getPrefix() + "§4Map konnte nicht hinzugefüt werden, weil kein Spawner gesetzt wurde.");
//					return;
//				}
				if(!(map.getVoteItem() == null)) {
					map2.addVoteItem(map.getVoteItem());
				}else{
					Bukkit.broadcastMessage(Main.getPrefix() + "§eMap konnte nicht hinzugefüt werden, weil kein VoteItem gesetzt wurde.");
					return;
				}
				
				if(map.getTeams() != null) {
					for(Team team : map.getTeams()) {
						map2.addTeam(team);
					}
				}
				
				if(map.getAllSpawnerfromtheMap() != null) {
					for(Spawner spawner : map.getAllSpawnerfromtheMap()) {
						map2.addSpawner(spawner);
					}
				}
				
				if(map.getShopsLocations() == null) {
					HashMap<Location, FakePlayer> lfp = map.getShopsLocations();
					map2.setShopsLocations(lfp);
				}
				
				maps.add(map2);
				Bukkit.broadcastMessage(Main.getPrefix() + "§aMap wurde erfolgreich hinzugefügt.");
				return;
			}else{
				Bukkit.broadcastMessage(Main.getPrefix() + "§eMap konnte nicht hinzugefügt werden.");
				return;
			}
		}
	}
	
	public void setMaxPlayers(int maxPlayer) {
		this.maxPlayers = maxPlayer;
	}
	
	public void setGameName(String name) {
		this.name = name;
	}
	
	public void removeMap(Map map) {
		if(maps.contains(map)) {
			maps.remove(map);
			Bukkit.broadcastMessage(Main.getPrefix() + "§aMap erfolgreich entfernt.");
			return;
		}
	}
	
	public void switchBuildMode() {
		if(isGameReady) {
			if(isBuildMode) {
				isBuildMode = false;
			}else{
				isBuildMode = true;
			}
		}
	}
	
	public void shutdownServer(Player p) {
		
		if(checkIsGameReady(p)) {
			if(!this.file.exists()) {
				try {
					this.file.createNewFile();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			YamlConfiguration cfg = YamlConfiguration.loadConfiguration(this.file);
			cfg.set("Game.Name", this.name);
			cfg.set("Game.MaxPlayers", this.maxPlayers);
		}else{
			if(this.file.exists() && !isGameReady && !isBuildMode) {
				this.file.delete();
			}
		}
	}
	
	public int getMaxPlayers() {
		return maxPlayers;
	}
	
	public Team getTeamOfBed(Block bed) {
		for(Team teams : currentMap.getTeams()) {
			if(teams.getBedBack().equals(bed) || teams.getBedHead().equals(bed)) {
				return teams;
			}
		}
		return null;
	}
	
	public void setGameState(GameState gameState) {
		this.gameState = gameState;
	}
	
	public GameState getGameState() {
		return this.gameState;
	}
	
	public ArrayList<Map> getAllMaps() {
		return this.maps;
	}
	
	public Map getMapwithMapName(String mapName) {
		for(Map map : this.maps) {
			if(map.getMapName().equalsIgnoreCase(mapName)) {
				return map;
			}
		}
		return null;
	}
	
	public String getGameName() {
		return this.name;
	}
	
	private boolean checkIsGameReady(Player p) {
		if(this.maps.size() > 0) {
			//Maps Check
			boolean check = true;
			ArrayList<Map> breakMaps = new ArrayList<>();
			for(Map map : this.maps) {
				if(Bukkit.getWorld(map.getRealMapName()) == null) {
					p.sendMessage("§eFür die Map(" + map.getMapName() + ") wurde keine Karte gefunden. Und wird entfernt.");
					check = false;
					continue;
				}
				if(map.getAllSpawnerfromtheMap().size() == 0) {
					p.sendMessage("§eDie Map(" + map.getMapName() + ") hat keine Spawner.");
					if(!breakMaps.contains(map)) {
						breakMaps.add(map);
					}
					check = false;
					continue;
				}
				if(map.getTeams().size() == 0) {
					p.sendMessage("§eDie Map(" + map.getMapName() + ") hat keine Teams.");
					if(!breakMaps.contains(map)) {
						breakMaps.add(map);
					}
					check = false;
					continue;
				}
			}
			
			if(check == false) {
				p.sendMessage("§eDu musst bei den Maps die unter dieser Nachricht aufgelistet werden überprüfen ob dort auch wirklich Spawner und Teams gesetzt wurden.");
				String message = "";
				for(Map map : breakMaps) {
					message = message + map.getMapName() + ", ";
				}
				message = message.substring(0, message.length() - 2);
				p.sendMessage(message);
				p.sendMessage("§eDu kannst dich mit /bw tp (MapName) zu der Map teleportieren.");
				return false;
			}
			
			//Maps Check End
			//Team Check
			ArrayList<Map> breakMaps2 = new ArrayList<>();
			for(Map map : this.maps) {
				for(Team team : map.getTeams()) {
					if(team.getSpawn() == null) {
						p.sendMessage("§eDas Team(" + team.getTeamenum().getPrefix() + ") hat kein Spawn. Team wurde entfernt.");
						check = false;
						breakMaps2.add(map);
						continue;
					}
					if(team.getBedBack() == null || team.getBedHead() == null) {
						p.sendMessage("§eDas Team(" + team.getTeamenum().getPrefix() + ") hat kein Bed. Team wurde entfernt.");
						check = false;
						breakMaps2.add(map);
						continue;
					}
				}
			}
			
			if(check == false) {
				p.sendMessage("§eDie Maps die unter dieser Nachricht aufgelistet werden habe ein oder mehere defekte Teams. Bitte setzte sie noch mal.");
				String message = "";
				for(Map map : breakMaps2) {
					message = message + map.getMapName() + ", ";
				}
				message = message.substring(0, message.length() - 2);
				p.sendMessage(message);
				return false;
			}
			
			//Team Check End
			//Spawner Check
			
			ArrayList<Spawner> newSet = new ArrayList<>();
			ArrayList<Map> newMaps = new ArrayList<>();
			ArrayList<Map> tmp = this.maps;
			for(Map map : tmp) {
				boolean b = false;
				for(Spawner spawner : map.getAllSpawnerfromtheMap()) {
					if(spawner.getItem() == null) {
						
						Spawner nnew = new Spawner(spawner.getSpawnerEnum(), spawner.getLoc());
						
						nnew.setItem(new ItemStack(spawner.getSpawnerEnum().getMaterial()));
						newSet.add(nnew);
						if(!newMaps.contains(map)) {
							newMaps.add(map);
						}
						if(this.maps.contains(spawner)) {
							this.maps.remove(spawner);
						}
						b = true;
					}
					
					if(spawner.getSpawnerID() == "" || spawner.getSpawnerID() == null) {
						Spawner nnew = new Spawner(spawner.getSpawnerEnum(), spawner.getLoc());
						
						nnew.generateSpawnerID();
						newSet.add(nnew);
						if(!newMaps.contains(map)) {
							newMaps.add(map);
						}
						if(this.maps.contains(spawner)) {
							this.maps.remove(spawner);
						}
						b = true;
					}
					if(b) {
						newSet.add(spawner);
						if(!newMaps.contains(map)) {
							newMaps.add(map);
						}
						if(this.maps.contains(spawner)) {
							this.maps.remove(spawner);
						}
					}
				}
			}
			
			for(Map map : newMaps) {
				map.removeAllSpawner();
				for(Spawner spawner : newSet) {
					map.addSpawner(spawner);
				}
				this.maps.add(map);
			}
			
			if(this.name == "" || this.name == null) {
				p.sendMessage("Du musst zuerst ein Spiel erstellen. Oder es neu erstellen.");
				return false;
			}
			
		if(this.maxPlayers == 0) {
			p.sendMessage("Du hast keine Maximale Spieleranzahl eingegen. Bitte erstelle das Spiel neu.");
			return false;
		}
		
		if(this.lobbyLocation == null) {
			p.sendMessage("Du hast kein Lobby-Spawn gesetzt.");
			return false;
		}
		
		return true;
		}else{
			p.sendMessage("§eDu musst erst eine Map hinzufügen.");
			return false;
		}
//		//TODO: Wenn alles Fertig ist mach den Programmiere den Check.
//		return true;
	}
}
