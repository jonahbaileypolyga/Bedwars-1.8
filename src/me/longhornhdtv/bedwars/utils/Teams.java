package me.longhornhdtv.bedwars.utils;

import org.bukkit.Material;

public enum Teams {
	BLACK(Material.STAINED_CLAY, 15, "§0SCHWARTZ"),
	WHITE(Material.STAINED_CLAY, 0, "§fWEISS"),
	RED(Material.STAINED_CLAY, 14, "§cROT"),
	YELLOW(Material.STAINED_CLAY, 4, "§eGELB"),
	GREEN(Material.STAINED_CLAY, 5, "§aGRÜN"),
	PURPLE(Material.STAINED_CLAY, 10, "§5LILA"),
	ORANGE(Material.STAINED_CLAY, 1, "§6ORANGE"),
	BLUE(Material.STAINED_CLAY, 11, "§9BLAU");
	
	//8x1 Schwartz Weiß Rot Gelb Grün Lila Orange Blau
	//2x4 BLau Gelb Rot Lila
	//4x4 BLau Gelb Rot Lila
	//4x8 BLau Gelb Rot Lila
	
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
