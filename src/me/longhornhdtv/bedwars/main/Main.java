package me.longhornhdtv.bedwars.main;

import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.WorldCreator;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.java.JavaPlugin;

import me.longhornhdtv.bedwars.commands.Command_Setup;
import me.longhornhdtv.bedwars.listeners.DeathEvent;
import me.longhornhdtv.bedwars.utils.Game;
import me.longhornhdtv.bedwars.utils.IntenvoryUtil;
import me.longhornhdtv.bedwars.utils.Map;
import me.longhornhdtv.bedwars.utils.PacketReader;

public class Main extends JavaPlugin implements Listener{
		
	public static ConsoleCommandSender CCS = Bukkit.getConsoleSender();
	private static String prefix = "§8| §eBedWars §8* ";
	private static File MainFolder = new File("plugins//Bedwars");
	private static File MainConfig = new File(MainFolder + "//config.yml");
	public static File GameFile = new File(MainFolder + "//game.data");
	private static YamlConfiguration cfg;
	public static Game game;
	public static boolean isDebug = true;
	public static ArrayList<Map> debugMaps = new ArrayList<>();
	
	@Override
	public void onEnable() {
		if(isDebug) {
//			this.getCommand("Bedwars").setExecutor(new MainCommand());
			game = new Game(GameFile);
			this.getServer().getPluginManager().registerEvents(this, this);
			this.getCommand("setup").setExecutor(new Command_Setup());
	        IntenvoryUtil.bloecke_mat.put(11, Material.CLAY_BRICK);
	        IntenvoryUtil.bloecke_mat.put(12, Material.CLAY_BRICK);
	        IntenvoryUtil.bloecke_mat.put(13, Material.IRON_INGOT);
//	        IntenvoryUtil.bloecke_mat.put(14, Material.CLAY_BRICK);
	        IntenvoryUtil.bloecke_mat.put(15, Material.CLAY_BRICK);
	        IntenvoryUtil.bloecke_price.put(11, 1);
	        IntenvoryUtil.bloecke_price.put(12, 7);
	        IntenvoryUtil.bloecke_price.put(13, 3);
//	        IntenvoryUtil.bloecke_price.put(14, 4);
	        IntenvoryUtil.bloecke_price.put(15, 4);
	        IntenvoryUtil.ruestung_mat.put(9, Material.CLAY_BRICK);
	        IntenvoryUtil.ruestung_mat.put(10, Material.CLAY_BRICK);
	        IntenvoryUtil.ruestung_mat.put(11, Material.CLAY_BRICK);
	        IntenvoryUtil.ruestung_mat.put(14, Material.IRON_INGOT);
	        IntenvoryUtil.ruestung_mat.put(15, Material.IRON_INGOT);
	        IntenvoryUtil.ruestung_mat.put(16, Material.IRON_INGOT);
	        IntenvoryUtil.ruestung_mat.put(17, Material.IRON_INGOT);
	        IntenvoryUtil.ruestung_price.put(9, 1);
	        IntenvoryUtil.ruestung_price.put(10, 1);
	        IntenvoryUtil.ruestung_price.put(11, 1);
	        IntenvoryUtil.ruestung_price.put(14, 1);
	        IntenvoryUtil.ruestung_price.put(15, 3);
	        IntenvoryUtil.ruestung_price.put(16, 5);
	        IntenvoryUtil.ruestung_price.put(17, 7);
	        this.getServer().getPluginManager().registerEvents(new IntenvoryUtil(), this);
	        this.getServer().getPluginManager().registerEvents(new DeathEvent(), this);
			CCS.sendMessage("§8| §eBedWars §8*  §aIst nun im Developer Modus.");
			return;
		}
		if(!MainFolder.exists()) {
			MainFolder.mkdirs();
		}
		if(!MainConfig.exists()) {
			try {
				MainConfig.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
			YamlConfiguration cfg = YamlConfiguration.loadConfiguration(MainConfig);
			loadDefaults(cfg, "config.yml");
			try {
				cfg.save(MainConfig);
			} catch (IOException e) {
				e.printStackTrace();
			}
			this.cfg = cfg;
		}
		game = new Game(GameFile);
        IntenvoryUtil.bloecke_mat.put(11, Material.CLAY_BRICK);
        IntenvoryUtil.bloecke_mat.put(12, Material.CLAY_BRICK);
        IntenvoryUtil.bloecke_mat.put(13, Material.IRON_INGOT);
//        IntenvoryUtil.bloecke_mat.put(14, Material.CLAY_BRICK);
        IntenvoryUtil.bloecke_mat.put(15, Material.CLAY_BRICK);
        IntenvoryUtil.bloecke_price.put(11, 1);
        IntenvoryUtil.bloecke_price.put(12, 7);
        IntenvoryUtil.bloecke_price.put(13, 3);
//        IntenvoryUtil.bloecke_price.put(14, 4);
        IntenvoryUtil.bloecke_price.put(15, 4);
		CCS.sendMessage("§8| §eBedWars §8*  §aWurde geladen und Aktiviert !");
	}

	@Override
	public void onDisable() {
		CCS.sendMessage("§8| §eBedWars §8*  §cWurde deaktiviert !");
		
		
		
	}
	
	@EventHandler
	public void onJoin(PlayerJoinEvent e) {
		PacketReader pr = new PacketReader(e.getPlayer());
		pr.inject();
		e.setJoinMessage("§6TestJoin Massge: Player: " + e.getPlayer().getDisplayName());
	}
	
	@EventHandler
	public void onQuit(PlayerQuitEvent e) {
		PacketReader pr = new PacketReader(e.getPlayer());
		pr.uninject();
	}
	
	public static void reloadCustomConfig() {
		cfg = YamlConfiguration.loadConfiguration(MainConfig);
	}
	
	public static YamlConfiguration getMainConfig() {
		return cfg;
	}
	
	public static String getPrefix() {
		return prefix;
	}
	
	private void loadDefaults(FileConfiguration fcFile, String resourceFile) {
		Reader defConfigStream;
		try {
			defConfigStream = new InputStreamReader(this.getResource(resourceFile), "UTF8");
			if(defConfigStream != null) {
				YamlConfiguration defConfig = YamlConfiguration.loadConfiguration(defConfigStream);
				fcFile.setDefaults(defConfig);
				defConfigStream.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void copyFilesInDirectory(File from, File to) throws IOException {
		if(!to.exists()) {
			to.mkdirs();	
		}
		for (File file : from.listFiles()) {
			if (file.isDirectory()) {
				copyFilesInDirectory(file, new File(to.getAbsolutePath() + "/" + file.getName()));
			} else {
				File n = new File(to.getAbsolutePath() + "/" + file.getName());
				Files.copy(file.toPath(), n.toPath(), StandardCopyOption.REPLACE_EXISTING);
			}
		}
	}
	private static void copyFileToDirectory(File file, File to) throws IOException {
		if(!to.exists()) {
			to.mkdirs();	
		}
		File n = new File(to.getAbsolutePath() + "/" + file.getName());
		Files.copy(file.toPath(), n.toPath(), StandardCopyOption.REPLACE_EXISTING);
	}
	
	public static boolean loadWorld(String name) {
		if(!Main.getPlugin(Main.class).getServer().getWorlds().contains((Main.getPlugin(Main.class).getServer().getWorld(name)))) {
			new WorldCreator(name).createWorld();
			return true;
		}
		return false;
	}
	
	public static void unloadWold(String name) {
		if(Main.getPlugin(Main.class).getServer().getWorlds().contains((Main.getPlugin(Main.class).getServer().getWorld(name)))) {
			
		}
	}
}
