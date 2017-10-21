package me.longhornhdtv.bedwars.utils;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

public class Spawner {
	
	private String spawnerID;
	private SpawnerEnum spawnerEnum;
	private Location loc;
	private ItemStack item;
	
	public Spawner(SpawnerEnum spawnerEnum, Location loc) {
		this.spawnerEnum = spawnerEnum;
		this.loc = loc;
	}
	
	public void setItem(ItemStack item) {
		this.item = item;
	}
	
	public void setSpawnerID(UUID uuid) {
		this.spawnerID = uuid.toString();
	}
	
	public void dropItem() {
		Bukkit.getWorld(loc.getWorld().getName()).dropItem(loc, item).setVelocity(new Vector(0, 0.012, 0));;
	}
	
	public void generateSpawnerID() {
		this.spawnerID = UUID.randomUUID().toString();
	}
	
	//Getters

	public SpawnerEnum getSpawnerEnum() {
		return spawnerEnum;
	}

	public Location getLoc() {
		return loc;
	}

	public ItemStack getItem() {
		return item;
	}
	
	public String getSpawnerID() {
		return spawnerID;
	}
	
}
