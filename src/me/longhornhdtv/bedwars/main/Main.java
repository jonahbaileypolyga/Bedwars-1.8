package me.longhornhdtv.bedwars.main;

import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import me.longhornhdtv.bedwars.utils.Game;
import me.longhornhdtv.bedwars.utils.Map;

public class Main extends JavaPlugin{
		
	public static ConsoleCommandSender CCS = Bukkit.getConsoleSender();
	private static String prefix = "§8| §BedWars &8* ";
	private static File MainFolder = new File("plugins//Bedwars");
	private static File MainConfig = new File(MainFolder + "//config.yml");
	private static File GameFile = new File(MainFolder + "//game.data");
	private static YamlConfiguration cfg;
	public static Game game;
	public static boolean isDebug = true;
	public static ArrayList<Map> debugMaps = new ArrayList<>();
	
	@Override
	public void onEnable() {
		if(isDebug) {
//			this.getCommand("Bedwars").setExecutor(new MainCommand());
			CCS.sendMessage("§8| §BedWars &8*  §aIst nun im Developer Modus.");
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
		CCS.sendMessage("§8| §BedWars &8*  §aWurde geladen und Aktiviert !");
	}

	@Override
	public void onDisable() {
		CCS.sendMessage("§8| §BedWars &8*  §cWurde deaktiviert !");
		
		
		
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
}
