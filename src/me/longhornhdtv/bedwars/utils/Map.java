package me.longhornhdtv.bedwars.utils;

import java.util.ArrayList;
import java.util.HashMap;

//import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.inventory.ItemStack;

public class Map {
	
	private String mapName;
	private ItemStack voteItem;
	private ArrayList<Spawner> spawners;
	private ArrayList<Team> teams;
	private String realMapName;
	private HashMap<Location, Block> changesBlock;
	private HashMap<Location, FakePlayer> shopsLocations;
	
//	public Map(String mapName) {
//		this.mapName = mapName;
//	}
	
	public Map(String mapName, String realMapName) {
		this.mapName = mapName;
		this.realMapName = realMapName;
		this.spawners = new ArrayList<>();
		this.teams = new ArrayList<>();
		this.changesBlock = new HashMap<>();
		this.shopsLocations = new HashMap<>();
	}
	
	public void setRealMapName(String mapRealName) {
		this.realMapName = mapRealName;
	}
	
	public void setMapname(String mapName) {
		this.mapName = mapName;
	}
	
//	public void setFolderMap(String realMapName) {
//		this.realMapName = realMapName;
//	}
	
	public String getRealMapName() {
		return this.realMapName;
	}
	
	public void addVoteItem(ItemStack item) {
		this.voteItem = item;
	}
	
	public void removeVoteItem() {
		this.voteItem = null;
	}
	
	public ItemStack getVoteItem() {
		if(this.voteItem != null) {
			return this.voteItem;
		}
		return null;
	}
	
	public void addSpawner(Spawner spawner) {
		if(!spawners.contains(spawner)) {
			spawners.add(spawner);
		}
	}
	
	public void removeSpawner(Spawner spawner) {
		if(spawners.contains(spawner)) {
			spawners.remove(spawner);
		}
	}
	
	public void removeAllSpawner() {
		spawners.clear();
	}
	
	public Team getTeam(Teams team) {
		if(teams == null) {
			return null;
		}
		for(Team teams : this.teams) {
			if(teams.teamsenum.equals(team)) {
				return teams;
			}else{
				continue;
			}
		}
		return null;
	}
	
	public ArrayList<Team> getTeams() {
		return this.teams;
	}
	
	public void addTeam(Team team) {
		this.teams.add(team);
//		Bukkit.broadcastMessage("DEBUG: addMap: " + team.getTeamenum().getPrefix());
	}
	
	public void removeTeam(Team team) {
		this.teams.remove(team);
	}
	
	public ArrayList<Spawner> getAllSpawnerfromtheMap() {
		return spawners;
	}
	
	public ArrayList<Spawner> getAllSpawnerwithSpawnerEnum(SpawnerEnum spawnerEnum) {
		ArrayList<Spawner> EnumSpawner = new ArrayList<>();
		if(this.spawners == null) { return null; }
		for(Spawner spawner : this.spawners) {
			if(spawner.getSpawnerEnum().equals(spawnerEnum)) {
				EnumSpawner.add(spawner);
			}
		}
		return EnumSpawner;
	}
	
	public Spawner getSpawnerwithSpawnerID(String SpawnerID) {
		if(this.spawners == null) { return null; }
		for(Spawner spawner : this.spawners) {
			if(spawner.getSpawnerID().equalsIgnoreCase(SpawnerID)) {
				return spawner;
			}
		}
		return null;
	}
	
	public String getMapName() {
		return mapName;
	}
	
	public void addBlock(Location loc, Block before) {
		this.changesBlock.put(loc, before);
	}
	
	public HashMap<Location, Block> getBloecks() {
		return this.changesBlock;
	}

	public HashMap<Location, FakePlayer> getShopsLocations() {
		return shopsLocations;
	}

	public void setShopsLocations(HashMap<Location, FakePlayer> shopsLocations) {
		this.shopsLocations = shopsLocations;
	}
	
	public void addShopLocation(Location loc, FakePlayer fp) {
		this.shopsLocations.put(loc, fp);
	}
	
}
