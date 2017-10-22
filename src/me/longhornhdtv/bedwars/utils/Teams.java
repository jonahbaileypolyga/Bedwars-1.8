package me.longhornhdtv.bedwars.utils;

import org.bukkit.Material;

public enum Teams {
	BLACK(Material.WOOL, 15, "§0SCHWARTZ"),
	WHITE(Material.WOOL, 0, "§fWEISS"),
	RED(Material.WOOL, 14, "§cROT"),
	YELLOW(Material.WOOL, 4, "§eGELB"),
	GREEN(Material.WOOL, 5, "§aGRÜN"),
	PURPLE(Material.WOOL, 10, "§5LILA"),
	ORANGE(Material.WOOL, 1, "§6ORANGE"),
	BLUE(Material.WOOL, 11, "§9BLAU"),
	
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
