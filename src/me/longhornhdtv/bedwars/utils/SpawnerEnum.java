package me.longhornhdtv.bedwars.utils;

import org.bukkit.Material;

public enum SpawnerEnum {
	BRONZE(Material.CLAY_BRICK), SILVER(Material.IRON_INGOT), GOLD(Material.GOLD_INGOT);
	
	private Material mat;
	
	SpawnerEnum(Material mat) {
		this.mat = mat;
	}
	
	public Material getMaterial() {
		return this.mat;
	}
	
	public void setMaterial(Material mat) {
		this.mat = mat;
	}
}
