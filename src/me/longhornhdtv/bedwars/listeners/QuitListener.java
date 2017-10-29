package me.longhornhdtv.bedwars.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import me.longhornhdtv.bedwars.utils.PacketReader;

public class QuitListener implements Listener{
	
    @EventHandler
    public void onQuit(PlayerQuitEvent e) {
        Player p = e.getPlayer();
        PacketReader pr = new PacketReader(p);
        pr.uninject();
        e.setQuitMessage("§6" + p.getDisplayName() + " §7hat den Server verlassen§8!");
    }
}
