package fr.auretechno;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import fr.auretechno.spawn.commands.BuildingModeCommandExecutor;
import fr.auretechno.spawn.commands.SpawnCommandExecutor;
import fr.auretechno.spawn.listener.SpawnPluginListener;

public class SpawnMain extends JavaPlugin {
	
	private static final Logger LOGGER = LogManager.getLogger("auretechno/spawn");
	public static final SpawnMain INSTANCE = null;
	
	private Map<UUID, Boolean> builders = new HashMap<UUID, Boolean>();
	
	@Override
	public void onEnable() {
		sendInfo("Plugin State - Enabling");
		saveDefaultConfig();
		getServer().getMessenger().registerOutgoingPluginChannel(this, "BungeeCord");
		
		PluginManager manager = getServer().getPluginManager();
		manager.registerEvents(new SpawnPluginListener(this), this);
		
		getCommand("spawn").setExecutor(new SpawnCommandExecutor());
		getCommand("building-mode").setExecutor(new BuildingModeCommandExecutor(this));
		getCommand("building-mode").setTabCompleter(new BuildingModeCommandExecutor.BuildingModeCommandTabCompleter());

		
		for(String section : getConfig().getKeys(false)) {
			builders.put(UUID.fromString(section), getConfig().getBoolean(section + ".builder"));
			System.out.println(section);
		}
	}
	
	@Override
	public void onLoad() {
		sendInfo("Plugin State - Loading");

	}
	
	@Override
	public void onDisable() {
		sendInfo("Plugin State - Disabling");
	}
	
	public static void sendInfo(String txt) {
		LOGGER.info("[auretechno/spawn] " + txt);
	}
	
	public Map<UUID, Boolean> getBuilders() {
		return builders;
	}
}