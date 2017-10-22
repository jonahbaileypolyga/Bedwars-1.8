package me.longhornhdtv.bedwars.utils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.configuration.file.YamlConfiguration;
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
					boolean b1 = false;
					Map map = new Map(name);
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
				for(Map map : this.maps) {
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
			if(Bukkit.getWorld(map.getMapName()) != null) {
				Map map2 = new Map(Bukkit.getWorld(map.getMapName()).getName());
				for(Spawner spawner : map.getAllSpawnerfromtheMap()) {
					map2.addSpawner(spawner);
				}
				if(map2.getAllSpawnerfromtheMap() == null) {
					Bukkit.broadcastMessage(Main.getPrefix() + "§4Map konnte nicht hinzugefüt werden, weil kein Spawner gesetzt wurde.");
					return;
				}
				if(!(map.getVoteItem() == null)) {
					map2.addVoteItem(map.getVoteItem());
				}else{
					Bukkit.broadcastMessage(Main.getPrefix() + "§4Map konnte nicht hinzugefüt werden, weil kein VoteItem gesetzt wurde.");
					return;
				}
				maps.add(map2);
				Bukkit.broadcastMessage(Main.getPrefix() + "§aMap wurde erfolgreich hinzugefügt.");
				return;
			}else{
				Bukkit.broadcastMessage(Main.getPrefix() + "§4Map konnte nicht hinzugefügt werden.");
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
	
	public void shutdownServer() {
		YamlConfiguration cfg = YamlConfiguration.loadConfiguration(this.file);
		if(checkIsGameReady()) {
			if(!this.file.exists()) {
				try {
					file.createNewFile();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			cfg.set("Game.Name", this.name);
			cfg.set("Game.MaxPlayers", this.maxPlayers);
		}else{
			this.file.delete();
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
	
	private boolean checkIsGameReady() {
		//TODO: Wenn alles Fertig ist mach den Programmiere den Check.
		return true;
	}
}
