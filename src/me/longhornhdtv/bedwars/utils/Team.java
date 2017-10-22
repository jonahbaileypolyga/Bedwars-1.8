package me.longhornhdtv.bedwars.utils;

import java.util.ArrayList;

import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

public class Team {
	
	Teams teamsenum;
	Location spawn;
	Block bedHead;
	Block bedBack;
	int maxTeamSize;
	ArrayList<Player> playersinteam = new ArrayList<>();
	
	
	public Team(Teams teamenum, int maxTeeamSize) {
		this.teamsenum = teamenum;
		this.maxTeamSize = maxTeeamSize;
	}
	
	public Team(Teams teameunm, Location spawn, Block bedLocHead, Block bedLocBack, int maxTeamSize) {
		this.teamsenum = teameunm;
		this.spawn = spawn;
		this.bedHead = bedLocHead;
		this.bedBack = bedLocBack;
		this.maxTeamSize = maxTeamSize;
	}
	
	public void setSpawn(Location spawn) {
		this.spawn = spawn;
	}
	
	public Location getSpawn() {
		return this.spawn;
	}
	
	public void setBed(Block back, Block head) {
		this.bedBack = back;
		this.bedHead = head;
	}
	
	public Block getBedBack() {
		return this.bedBack;
	}
	
	public Block getBedHead() {
		return this.bedHead;
	}
	
	public void setMaxSize(int maxSize) {
		this.maxTeamSize = maxSize;
	}
	
	public int getMaxSize() {
		return this.maxTeamSize;
	}
	
	public void setTeamenum(Teams teamenum) {
		this.teamsenum = teamenum;
	}
	
	public Teams getTeamenum() {
		return this.teamsenum;
	}
	
	public boolean addPlayer(Player p) {
		if(playersinteam != null) {
			for(Player t : playersinteam) {
				if(t.getUniqueId().equals(p.getUniqueId())) {
					return false;
				}
			}
			playersinteam.add(p);
			return true;
		}else{
			playersinteam.add(p);
			return true;
		}
	}
	
	public boolean removePlayer(Player p) {
		if(playersinteam != null) {
			for(Player t : playersinteam) {
				if(t.getUniqueId().equals(p.getUniqueId())) {
					playersinteam.remove(t);
					return true;
				}
			}
			return false;
		}else{
			return false;
		}
	}
}
