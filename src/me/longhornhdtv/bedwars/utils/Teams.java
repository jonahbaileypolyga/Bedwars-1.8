package me.longhornhdtv.bedwars.utils;

import org.bukkit.Material;

public enum Teams {
	WHITE(Material.WOOL, 0, "WEISS"),
	ORANGE(Material.WOOL, 1, "ORANGE"),
	MAGENTA(Material.WOOL, 2, "MAGENTA"),
	LIGHTBLUE(Material.WOOL, 3, "HELL BLAU");
	
	
	
	private Material mat;
	private int metaID;
	private String prefix;
	
	Teams(Material mat, int metaID, String prefix) {
		this.mat = mat;
		this.metaID = metaID;
		this.prefix = prefix;
	}
	
	public Material getMaterial() {
		return this.mat;
	}
	
	public void setMaterial(Material mat) {
		this.mat = mat;
	}
	
	public int getMetaID() {
		return this.metaID;
	}
	
	public void setMetaID(int metaID) {
		this.metaID = metaID;
	}
	
	public String getPrefix() {
		return this.prefix;
	}
	
	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}
}
