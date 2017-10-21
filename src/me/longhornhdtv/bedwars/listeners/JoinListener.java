package me.longhornhdtv.bedwars.listeners;

import java.util.ArrayList;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerLoginEvent.Result;
import org.bukkit.event.server.ServerListPingEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import me.longhornhdtv.bedwars.main.Main;
import me.longhornhdtv.bedwars.utils.Game;
import me.longhornhdtv.bedwars.utils.GameState;

public class JoinListener implements Listener{
	
	@EventHandler
	public void onLogging(PlayerLoginEvent e) {
		if(Main.game.getGameState().equals(GameState.FULL_LOBBY) || Main.game.getGameState().equals(GameState.LOBBY)) {
			if(Main.game.getMaxPlayers() - Bukkit.getOnlinePlayers().size() > 0) {
				Main.game.setGameState(GameState.LOBBY);
				e.setResult(Result.ALLOWED);
				e.allow();
				e.getPlayer().getAddress().getHos
			}else{
				if(e.getPlayer().hasPermission(Main.getMainConfig().getString("Config.JoinonFullLobbys"))){
					Main.game.setGameState(GameState.FULL_LOBBY);
					Player p2 = getNonPermiumPlayer();
					p2.kickPlayer("§6Du wurdest §l§4gekickt! §7Kaufe dir denn §6§lPREMIUM §6Rang §7um nicht gekickt zu werden.");
					e.setResult(Result.ALLOWED);
					e.allow();
				}else{
					Main.game.setGameState(GameState.FULL_LOBBY);
					e.setResult(Result.KICK_OTHER);
					e.disallow(Result.KICK_OTHER, "§6Spiel ist voll. §7Kaufe dir denn §6§lPREMIUM §6Rang §7um auf volle Lobbys/Minigames zu joinen.");
				}
			}
		}else
		if(Main.game.getGameState().equals(GameState.INGAME)) {
			if(e.getPlayer().hasPermission(Main.getMainConfig().getString("Config.Supporter-Permission"))) {
				e.setResult(Result.ALLOWED);
				e.allow();
			}else{
				e.setResult(Result.KICK_OTHER);
				e.disallow(Result.KICK_OTHER, "§6Du wurdest gekickt, weil das Spiel schon leuft.");
			}
		}else{
			e.setResult(Result.KICK_OTHER);
			e.disallow(Result.KICK_OTHER, "§6Der Server wird gerade Neu Gestartet.");
		}
	}
	
	public Player getNonPermiumPlayer() {
		ArrayList<Player> nonPremiumPlayers = new ArrayList<>();
		for(Player player : Bukkit.getOnlinePlayers()) {
			if(!player.hasPermission(Main.getMainConfig().getString("Config.JoinonFullLobbys"))) {
				nonPremiumPlayers.add(player);
			}
		}
		if(nonPremiumPlayers.isEmpty()) {
			return null;
		}
		Random r = new Random();
		return nonPremiumPlayers.get(r.nextInt(nonPremiumPlayers.size()));
	}
	
	@EventHandler
	public void onServerListPing(ServerListPingEvent e) {
		if(!Main.game.isGameReady || Main.game.isBuildMode) {
			e.setMaxPlayers(20);
			e.setMotd("§4WARTUNG");
		}
		if(Main.game.getGameState().equals(GameState.LOBBY) || Main.game.getGameState().equals(GameState.FULL_LOBBY)) {
			e.setMaxPlayers(Main.game.getMaxPlayers());
			e.setMotd("§aLOBBY");
		}else
		if(Main.game.getGameState().equals(GameState.INGAME)) {
			e.setMaxPlayers(Main.game.getMaxPlayers());
			e.setMotd("§7INGAME");
		}else{
			e.setMaxPlayers(Main.game.getMaxPlayers());
			e.setMotd("§6RESTART");
		}
	}
	
	@EventHandler
	public void onJoin(PlayerJoinEvent e) {
		Player p = e.getPlayer();
		
		if(Main.game.isGameReady) {
			e.setJoinMessage("§6" + p.getDisplayName() + " §7hat den Server betreten §8(§a" + Bukkit.getServer().getOnlinePlayers().size()+1 + "§8/§a" + Main.game.getMaxPlayers() + "§8)");
		}else{
			e.setJoinMessage("§6" + p.getDisplayName() + " §7hat den Server betreten §8(§a" + Bukkit.getServer().getOnlinePlayers().size()+1 + "§8/§a" + Bukkit.getMaxPlayers() + "§8)");
		}
		if(Main.game.isGameReady && !Main.game.isBuildMode) {
			ItemStack voteitem = new ItemStack(Material.MAP);
			ItemMeta meta = voteitem.getItemMeta();
	//		ArrayList<String> lore = new ArrayList<>();
	//		lore.add("Ha§allo");
			meta.setDisplayName("§f§oVoting");
	//		meta.setLore(lore);
			voteitem.setItemMeta(meta);
			p.getInventory().addItem(voteitem);
			p.getInventory().setItem(0, voteitem);
			
			ItemStack quititem = new ItemStack(Material.SLIME_BALL);
			ItemMeta meta1 = quititem.getItemMeta();
	//		ArrayList<String> lore = new ArrayList<>();
	//		lore.add("Ha§allo");
			meta1.setDisplayName("§f§oZurück zur §eLobby");
	//		meta.setLore(lore);
			quititem.setItemMeta(meta1);
			p.getInventory().addItem(quititem);
			p.getInventory().setItem(8, quititem);
		}
	}
}
