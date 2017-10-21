package me.longhornhdtv.bedwars.listeners;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class QuitListener implements Listener{
	
    @EventHandler
    public void onJoin(PlayerQuitEvent e) {

        Player p = e.getPlayer();

        e.setQuitMessage("§6" + p.getDisplayName() + " §7hat den Server verlassen§8!");


    }
	

}
