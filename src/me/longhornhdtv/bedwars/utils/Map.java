package me.longhornhdtv.bedwars.utils;

import java.util.ArrayList;

import org.bukkit.inventory.ItemStack;

public class Map {
	
	private String MapName;
	private ItemStack voteItem;
	private ArrayList<Spawner> spawners = new ArrayList<>();
	private ArrayList<Team> teams;
	
	public Map(String mapName) {
		this.MapName = mapName;
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
		return MapName;
	}
}
