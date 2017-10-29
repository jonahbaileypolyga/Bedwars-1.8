package me.longhornhdtv.bedwars.utils;

import java.util.List;
import java.util.UUID;
 
import net.minecraft.server.v1_8_R3.BlockPosition;
import net.minecraft.server.v1_8_R3.DataWatcher;
import net.minecraft.server.v1_8_R3.ItemStack;
import net.minecraft.server.v1_8_R3.MathHelper;
//import net.minecraft.server.v1_8_R3.MobSpawnerAbstract.a;
import net.minecraft.server.v1_8_R3.PacketPlayOutAnimation;
import net.minecraft.server.v1_8_R3.PacketPlayOutBed;
import net.minecraft.server.v1_8_R3.PacketPlayOutEntity.PacketPlayOutEntityLook;
import net.minecraft.server.v1_8_R3.PacketPlayOutEntityDestroy;
import net.minecraft.server.v1_8_R3.PacketPlayOutEntityEquipment;
import net.minecraft.server.v1_8_R3.PacketPlayOutEntityHeadRotation;
import net.minecraft.server.v1_8_R3.PacketPlayOutEntityStatus;
import net.minecraft.server.v1_8_R3.PacketPlayOutEntityTeleport;
import net.minecraft.server.v1_8_R3.PacketPlayOutNamedEntitySpawn;
import net.minecraft.server.v1_8_R3.PacketPlayOutPlayerInfo;
import net.minecraft.server.v1_8_R3.WorldSettings.EnumGamemode;
 
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_8_R3.util.CraftChatMessage;
import org.bukkit.entity.Player;
 
import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;

public class FakePlayer extends Reflections {

	private int entityID;
    private Location location;
    private GameProfile gameprofile;
   
   
    public FakePlayer(String name,Location location){
            entityID = (int)Math.ceil(Math.random() * 1000) + 2000;
            gameprofile = new GameProfile(UUID.randomUUID(), name);
            changeSkin();
            this.location = location.clone();
    }
    
    public FakePlayer(Location loc, int entityID, GameProfile gameProfile) {
    	this.entityID = entityID;
    	this.gameprofile = gameProfile;
    	changeSkin();
    	this.location = loc.clone();
    }
    
    public void setEntityID(int id) {
    	entityID = id;
    }
    
    public GameProfile getProfile() {
    	return gameprofile;
    }
   
    public void changeSkin(){
            String value = "eyJ0aW1lc3RhbXAiOjE1MDkyMzc0NjQ4NDksInByb2ZpbGVJZCI6IjMzODllOWFjNDM1MzQxODFiMTAxZTA5NjQxN2M0NTQzIiwicHJvZmlsZU5hbWUiOiJQbGFubG9zZXJKdW5nZSIsInNpZ25hdHVyZVJlcXVpcmVkIjp0cnVlLCJ0ZXh0dXJlcyI6eyJTS0lOIjp7Im1ldGFkYXRhIjp7Im1vZGVsIjoic2xpbSJ9LCJ1cmwiOiJodHRwOi8vdGV4dHVyZXMubWluZWNyYWZ0Lm5ldC90ZXh0dXJlLzdjY2IyMDI3NDJjOTc1NTkyM2UyYjc2ZWU4YTc3NzNkMDk3NjNmMDc1NjE0OWE2Y2IyZTllNWVmMDFlYmIifX19";
            String signature = "QSR8iu3jNlmKnQzwUortuGlutncHbqY6gcY1aURJ5IW0TaQR2aBeZT3LrivfjTtAZbJ2Ou9hIpB26pk4r0SlkUwdiGvcSzuoDstFeiNU6/jtXOeGo0m2M3eyLm5ySpHIF3q4dn/e/achouGNcN6BHZNbw2WHSJp/kTr+rsC0qgQ0HUnHQZgrRhw9R8ERHnliWFrjG9NWhxInjzQ3PnWDtzvwodlCfxXge76shNlBmTCrv5G6U06sSfysbWgffSHqOZLu9ZIuT6Mh4R/2aKCzuD6vPD5qXdfp/GcPfVa69oLCEDJ3SmZExP62GzZjjo6SxIdOVEccdiHnKG6TzCSxjYPy8hYGs6qMvtjZVMzBKzRszx/ZPrICAkQ/I4aCZcl5oabFBVkrdRNag7llvvwztYe3ddAfW716SYIoctR1c8Ax7quMgPfdfXrOqyFyp9WY/AKFr/yfVg0NYpu6BvV6IS1SQgOj477+JgD9XX8C1jYbJVdOM+1LoZgGeoAaWcVNcJVs5g43YfD2RC/mhmXmG9RVWCiKU8pbv0nk/famcby8jEt3YAQ4SM3FEw1WYwQ05Ej+dMAaSO99fYqFoHtMRpjbp6/WOuO9MUYl0eh43BkJ25uHOMEc2HzdigwX2c2SQaxwRmjTuhHgzy1+4e8Sqrfwd90pJKqGXD4pMFatsUM=";
            gameprofile.getProperties().put("textures", new Property("textures", value, signature));
    }
   
   
    public void animation(int animation){
            PacketPlayOutAnimation packet = new PacketPlayOutAnimation();
            setValue(packet, "a", entityID);
            setValue(packet, "b", (byte)animation);
            sendPacket(packet);
    }
   
    public void status(int status){
            PacketPlayOutEntityStatus packet = new PacketPlayOutEntityStatus();
            setValue(packet, "a", entityID);
            setValue(packet, "b", (byte)status);
            sendPacket(packet);
    }
   
    public void equip(int slot,ItemStack itemstack){
            PacketPlayOutEntityEquipment packet = new PacketPlayOutEntityEquipment();
            setValue(packet, "a", entityID);
            setValue(packet, "b", slot);
            setValue(packet, "c", itemstack);
            sendPacket(packet);
    }
   
    @SuppressWarnings("deprecation")
	public void sleep(boolean state){
            if(state){
                    Location bedLocation = new Location(location.getWorld(), 1, 1, 1);
                    PacketPlayOutBed packet = new PacketPlayOutBed();
                    setValue(packet, "a", entityID);
                    setValue(packet, "b", new BlockPosition(bedLocation.getX(),bedLocation.getY(),bedLocation.getZ()));
                   
                    for(Player pl : Bukkit.getOnlinePlayers()){
                            pl.sendBlockChange(bedLocation, Material.BED_BLOCK, (byte)0);
                    }

                    sendPacket(packet);
                    teleport(location.clone().add(0,0.3,0));
            }else{
                    animation(2);
                    teleport(location.clone().subtract(0,0.3,0));
            }
    }
   
    public void spawn(){
            PacketPlayOutNamedEntitySpawn packet = new PacketPlayOutNamedEntitySpawn();
           
            setValue(packet, "a", entityID);
            setValue(packet, "b", gameprofile.getId());
            setValue(packet, "c", getFixLocation(location.getX()));
            setValue(packet, "d", getFixLocation(location.getY()));
            setValue(packet, "e", getFixLocation(location.getZ()));
            setValue(packet, "f", getFixRotation(location.getYaw()));
            setValue(packet, "g", getFixRotation(location.getPitch()));
//            setValue(packet, "g", 90);
            setValue(packet, "h", 0);
            DataWatcher w = new DataWatcher(null);
            w.a(6,(float)20);
            w.a(10,(byte)127);
            setValue(packet, "i", w);
            addToTablist();
            sendPacket(packet);
            headRotation(location.getYaw(), location.getPitch());
    }
    
    public void spawnforPlayer(Player p) {
    	PacketPlayOutNamedEntitySpawn packet = new PacketPlayOutNamedEntitySpawn();
        
        setValue(packet, "a", entityID);
        setValue(packet, "b", gameprofile.getId());
        setValue(packet, "c", getFixLocation(location.getX()));
        setValue(packet, "d", getFixLocation(location.getY()));
        setValue(packet, "e", getFixLocation(location.getZ()));
        setValue(packet, "f", getFixRotation(location.getYaw()));
        setValue(packet, "g", getFixRotation(location.getPitch()));
//        setValue(packet, "g", 90);
        setValue(packet, "h", 0);
        DataWatcher w = new DataWatcher(null);
        w.a(6,(float)20);
        w.a(10,(byte)127);
        setValue(packet, "i", w);
        addToTablistforPlayer(p);
        sendPacket(packet, p);
        headRotationforPlayer(p, location.getYaw(), location.getPitch());
    }
   
    public void teleport(Location location){
            PacketPlayOutEntityTeleport packet = new PacketPlayOutEntityTeleport();
            setValue(packet, "a", entityID);
            setValue(packet, "b", getFixLocation(location.getX()));
            setValue(packet, "c", getFixLocation(location.getY()));
            setValue(packet, "d", getFixLocation(location.getZ()));
            setValue(packet, "e", getFixRotation(location.getYaw()));
            setValue(packet, "f", getFixRotation(location.getPitch()));

            sendPacket(packet);
            headRotation(location.getYaw(), location.getPitch());
            this.location = location.clone();
    }
   
    public void headRotation(float yaw,float pitch){
            PacketPlayOutEntityLook packet = new PacketPlayOutEntityLook(entityID, getFixRotation(yaw),getFixRotation(pitch) , true);
            PacketPlayOutEntityHeadRotation packetHead = new PacketPlayOutEntityHeadRotation();
            setValue(packetHead, "a", entityID);
            setValue(packetHead, "b", getFixRotation(yaw));
           

            sendPacket(packet);
            sendPacket(packetHead);
    }
    
    public void headRotationforPlayer(Player p, float yaw,float pitch){
        PacketPlayOutEntityLook packet = new PacketPlayOutEntityLook(entityID, getFixRotation(yaw),getFixRotation(pitch) , true);
        PacketPlayOutEntityHeadRotation packetHead = new PacketPlayOutEntityHeadRotation();
        setValue(packetHead, "a", entityID);
        setValue(packetHead, "b", getFixRotation(yaw));
       

        sendPacket(packet, p);
        sendPacket(packetHead, p);
}
   
    public void destroy(){
        PacketPlayOutEntityDestroy packet = new PacketPlayOutEntityDestroy(new int[] {entityID});
        rmvFromTablist();
        sendPacket(packet);
    }
    
    public void destryforPlayer(Player p) {
    	PacketPlayOutEntityDestroy packet = new PacketPlayOutEntityDestroy(new int[] {entityID});
    	rmvFromTablistforPlayer(p);
        sendPacket(packet, p);
    }
   
    public void addToTablist(){
            PacketPlayOutPlayerInfo packet = new PacketPlayOutPlayerInfo();
            PacketPlayOutPlayerInfo.PlayerInfoData data = packet.new PlayerInfoData(gameprofile, 1, EnumGamemode.NOT_SET, CraftChatMessage.fromString(gameprofile.getName())[0]);
            @SuppressWarnings("unchecked")
            List<PacketPlayOutPlayerInfo.PlayerInfoData> players = (List<PacketPlayOutPlayerInfo.PlayerInfoData>) getValue(packet, "b");
            players.add(data);
           
            setValue(packet, "a", PacketPlayOutPlayerInfo.EnumPlayerInfoAction.ADD_PLAYER);
            setValue(packet, "b", players);
           
            sendPacket(packet);
    }
    
    public void addToTablistforPlayer(Player p){
        PacketPlayOutPlayerInfo packet = new PacketPlayOutPlayerInfo();
        PacketPlayOutPlayerInfo.PlayerInfoData data = packet.new PlayerInfoData(gameprofile, 1, EnumGamemode.NOT_SET, CraftChatMessage.fromString(gameprofile.getName())[0]);
        @SuppressWarnings("unchecked")
        List<PacketPlayOutPlayerInfo.PlayerInfoData> players = (List<PacketPlayOutPlayerInfo.PlayerInfoData>) getValue(packet, "b");
        players.add(data);
       
        setValue(packet, "a", PacketPlayOutPlayerInfo.EnumPlayerInfoAction.ADD_PLAYER);
        setValue(packet, "b", players);
       
        sendPacket(packet, p);
}
   
    public void rmvFromTablist(){
            PacketPlayOutPlayerInfo packet = new PacketPlayOutPlayerInfo();
            PacketPlayOutPlayerInfo.PlayerInfoData data = packet.new PlayerInfoData(gameprofile, 1, EnumGamemode.NOT_SET, CraftChatMessage.fromString(gameprofile.getName())[0]);
            @SuppressWarnings("unchecked")
            List<PacketPlayOutPlayerInfo.PlayerInfoData> players = (List<PacketPlayOutPlayerInfo.PlayerInfoData>) getValue(packet, "b");
            players.add(data);
           
            setValue(packet, "a", PacketPlayOutPlayerInfo.EnumPlayerInfoAction.REMOVE_PLAYER);
            setValue(packet, "b", players);
           
            sendPacket(packet);
    }
    
    public void rmvFromTablistforPlayer(Player p) {
    	PacketPlayOutPlayerInfo packet = new PacketPlayOutPlayerInfo();
        PacketPlayOutPlayerInfo.PlayerInfoData data = packet.new PlayerInfoData(gameprofile, 1, EnumGamemode.NOT_SET, CraftChatMessage.fromString(gameprofile.getName())[0]);
        @SuppressWarnings("unchecked")
        List<PacketPlayOutPlayerInfo.PlayerInfoData> players = (List<PacketPlayOutPlayerInfo.PlayerInfoData>) getValue(packet, "b");
        players.add(data);
       
        setValue(packet, "a", PacketPlayOutPlayerInfo.EnumPlayerInfoAction.REMOVE_PLAYER);
        setValue(packet, "b", players);
       
        sendPacket(packet, p);
    }
   
    public int getFixLocation(double pos){
            return (int)MathHelper.floor(pos * 32.0D);
    }
   
    public int getEntityID() {
            return entityID;
    }
   
    public byte getFixRotation(float yawpitch){
            return (byte) ((int) (yawpitch * 256.0F / 360.0F));
    }
   
}
