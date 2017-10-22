package me.longhornhdtv.bedwars.utils;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.inventory.ItemStack;

public class Map {
	
	private String mapName;
	private ItemStack voteItem;
	private ArrayList<Spawner> spawners = new ArrayList<>();
	private ArrayList<Team> teams;
	private String realMapName;
	private HashMap<Location, Block> changesBlock = new HashMap<>();
	
	public Map(String mapName) {
		this.mapName = mapName;
	}
	
	public Map(String mapName, String realMapName) {
		this.mapName = mapName;
		this.realMapName = realMapName;
	}
	
	public void setMapname(String mapName) {
		this.mapName = mapName;
	}
	
	public void setFolderMap(String realMapName) {
		this.realMapName = realMapName;
	}
	
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
	
	public Team getTeam(Teams team) {
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
	}
	
	public void removeTeam(Team team) {
		this.teams.remove(team);
	}
	
	public ArrayList<Spawner> getAllSpawnerfromtheMap() {
		return spawners;
	}
	
	public ArrayList<Spawner> getAllSpawnerwithSpawnerEnum(SpawnerEnum spawnerEnum) {
		ArrayList<Spawner> EnumSpawner = new ArrayList<>();
		for(Spawner spawner : this.spawners) {
			if(spawner.getSpawnerEnum().equals(spawnerEnum)) {
				EnumSpawner.add(spawner);
			}
		}
		return EnumSpawner;
	}
	
	public Spawner getSpawnerwithSpawnerID(String SpawnerID) {
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
}
