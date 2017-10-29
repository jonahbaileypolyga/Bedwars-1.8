package me.longhornhdtv.bedwars.listeners;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.scheduler.BukkitRunnable;

import me.longhornhdtv.bedwars.main.Main;
import me.longhornhdtv.bedwars.utils.FakePlayer;
import me.longhornhdtv.bedwars.utils.Map;

public class DeathEvent implements Listener{

	@EventHandler
	public void onDeath(PlayerDeathEvent e) {
		Player p = e.getEntity();
		if(Main.isDebug) {
			p.sendMessage("Respawn");
			p.closeInventory();
			p.spigot().respawn();
			p.teleport(new Location(p.getLocation().getWorld(), 1.5D, 25D, -40.5D, -179, 1));
			onRespawn(p);
		}else{
			p.spigot().respawn();
		}
	}
	
	long timestamp = 0L;
	
	@SuppressWarnings("deprecation")
	public void onRespawn(Player e) {
		Player p = e.getPlayer();
		p.sendMessage("PlayerRespawnEvent call");
		if(Main.isDebug) {
			p.sendMessage("Yes is Debug");
			for(Map map : Main.game.getAllMaps()) {
				p.sendMessage("Map search");
				if(map.getMapName().equalsIgnoreCase(p.getLocation().getWorld().getName())) {
					p.sendMessage("Map equals");
					for(Location loc : map.getShopsLocations().keySet()) {
						FakePlayer fp1 = map.getShopsLocations().get(loc);
						timestamp = System.nanoTime();
						p.sendMessage("FakePlayer Respawn");
						Bukkit.getScheduler().runTaskLater(Main.getPlugin(Main.class), new BukkitRunnable() {
							
							@Override
							public void run() {
								double diff = (System.nanoTime()-timestamp)/1e9;
								p.sendMessage("Removed Tabliste FakePlayer Time: " + diff + " ms");
								fp1.rmvFromTablistforPlayer(p);
								timestamp = System.nanoTime();
							}
						}, 25);
						Bukkit.getScheduler().runTaskLater(Main.getPlugin(Main.class), new BukkitRunnable() {
							
							@Override
							public void run() {
								double diff = (System.nanoTime()-timestamp)/1e9;
								p.sendMessage("Spawned FakePlayer Time: " + diff + " ms");
								fp1.spawnforPlayer(p);
								timestamp = System.nanoTime();
							}
						}, 15);
						Bukkit.getScheduler().runTaskLater(Main.getPlugin(Main.class), new BukkitRunnable() {
							
							@Override
							public void run() {
								double diff = (System.nanoTime()-timestamp)/1e9;
								p.sendMessage("Destroyed FakePlayer Time: " + diff + " ms");
								fp1.destryforPlayer(p);
								timestamp = System.nanoTime();
							}
						}, 9);
					}
				}
			}
		}
	}
}
