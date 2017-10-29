package me.longhornhdtv.bedwars.utils;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;
import me.longhornhdtv.bedwars.main.Main;

import java.lang.reflect.Field;
import java.util.List;
 
import net.minecraft.server.v1_8_R3.Packet;

import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Egg;
import org.bukkit.entity.EnderPearl;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Sheep;
import org.bukkit.entity.TNTPrimed;

public class PacketReader {

	 Player player;
     Channel channel;
    
     public PacketReader(Player player) {
             this.player = player;
     }
    
     public void inject(){
             CraftPlayer cPlayer = (CraftPlayer)this.player;
             channel = cPlayer.getHandle().playerConnection.networkManager.channel;
             channel.pipeline().addAfter("decoder", "PacketInjector", new MessageToMessageDecoder<Packet<?>>() {@Override protected void decode(ChannelHandlerContext arg0, Packet<?> packet,List<Object> arg2) throws Exception {arg2.add(packet);readPacket(packet);}});
     }
    
     public void uninject(){
             if(channel.pipeline().get("PacketInjector") != null){
                     channel.pipeline().remove("PacketInjector");
             }
     }
    

     public void readPacket(Packet<?> packet){
             if(packet.getClass().getSimpleName().equalsIgnoreCase("PacketPlayInUseEntity")){
                     int id = (Integer)getValue(packet, "a");
                    Entity e = null;
                    for(Entity e1 : player.getLocation().getWorld().getEntities()) {
                    	if(e1.getEntityId() == id) {
                    		e = e1;
                    		break;
                    	}
                    }
                    boolean b1 = false;
                    if(e != null) {
                    	if(e instanceof Player || e instanceof ArmorStand || e instanceof TNTPrimed || e instanceof Sheep || e instanceof EnderPearl || e instanceof Egg || e instanceof Arrow) {
                    		if(e instanceof Player) {
                    			if(((Player) e).getDisplayName().equalsIgnoreCase("§6ALDI")) {
                    				b1 = false;
                    			}else{
                    				b1 = true;
                    			}
                    		}else{
                    			b1 = true;
                    		}
                    	}
                    }
                    if(b1) {
                    	return;
                    }
//                     System.out.println(getValue(packet, "action").toString());
//                    Bukkit.broadcastMessage(getValue(packet, "action").toString());
                     
                     
                     
                     FakePlayer interact = null;
                     for(Map map : Main.game.getAllMaps()) {
                    	 for(FakePlayer fp : map.getShopsLocations().values()) {
                    		 if(fp.getEntityID() == id) {
                    			 interact = fp;
                    			 break;
                    		 }
                    	 }
                     }
                     
                     if(interact != null) {
                    	 if(getValue(packet, "action").toString().equalsIgnoreCase("INTERACT")) {
//                    		 Inventory inv = Bukkit.createInventory(null, 9*2, "§6SHOP");
//                    		 ItemStack testItem = new ItemStack(Material.SANDSTONE);
//                    		 ItemMeta testItemMeta = testItem.getItemMeta();
//                    		 testItemMeta.setDisplayName("§6BLÖCKE");
//                    		 testItem.setItemMeta(testItemMeta);
//                    		 inv.setItem(0, testItem);
//                    		 player.openInventory(inv);
                    		 IntenvoryUtil.openMainInv(player);
                    	 }
                     }
//                     if(Main.game.getAllMaps().getEntityID() == id){
//                             if(getValue(packet, "action").toString().equalsIgnoreCase("ATTACK")){
//                                     Main.npc.animation(1);
//                             }else if(getValue(packet, "action").toString().equalsIgnoreCase("INTERACT")){
//                                     player.openInventory(player.getEnderChest());
//                             }
//                     }
                    
             }
     }
    

     public void setValue(Object obj,String name,Object value){
             try{
             Field field = obj.getClass().getDeclaredField(name);
             field.setAccessible(true);
             field.set(obj, value);
             }catch(Exception e){}
     }
    
     public Object getValue(Object obj,String name){
             try{
             Field field = obj.getClass().getDeclaredField(name);
             field.setAccessible(true);
             return field.get(obj);
             }catch(Exception e){}
             return null;
     }
    
}
