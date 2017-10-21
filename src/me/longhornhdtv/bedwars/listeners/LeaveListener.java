package me.longhornhdtv.bedwars.listeners;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

public class LeaveListener implements Listener{
	
	@EventHandler
	public void PlayerInteract(PlayerInteractEvent e){
		Player p = e.getPlayer();
		if(e.getAction() == Action.RIGHT_CLICK_AIR){
			
			
			if(p.getItemInHand() != null){
				
				if(p.getItemInHand().getType() == Material.SLIME_BALL){
					
					
					p.sendMessage("Du wirst nun zur §aLobby §7gesendet !");
					p.kickPlayer("");
				}
				
				
			}
		}else if(e.getAction() ==Action.RIGHT_CLICK_BLOCK){
			if(p.getItemInHand() != null){
				
				if(p.getItemInHand().getType() == Material.SLIME_BALL){
					
					p.kickPlayer(null);
					
					p.sendMessage("Du wirst nun zur §aLobby §7gesendet !");
				}	
		}
			

		}
	}
	

}
