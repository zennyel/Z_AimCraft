package com.zennyel.managers;

import com.zennyel.Z_AimLab;
import com.zennyel.commands.AimLabCommand;
import com.zennyel.database.PointsDB;
import com.zennyel.listeners.InventoryClickListener;
import com.zennyel.listeners.PlayerInteractListener;
import com.zennyel.listeners.PlayerJoinListener;
import org.bukkit.entity.Player;

import java.util.HashMap;

public class PluginManager {

    private Z_AimLab plugin;
    private PointsManager pointsManager;
    private PointsDB pointsDB;
    private ConfigManager configManager;
    private HashMap<Player, Boolean> isPlaying;
    private GUIManager guiManager;

    public PluginManager(Z_AimLab plugin) {
        this.isPlaying = new HashMap<>();
        this.plugin = plugin;
        this.pointsDB = new PointsDB(plugin);
        this.pointsDB.createPointsTable();
        this.pointsManager = new PointsManager(pointsDB, plugin);
        this.configManager = new ConfigManager(plugin.getConfig(), plugin);
        this.guiManager = new GUIManager(pointsManager, configManager, plugin);

    }

    public Z_AimLab getPlugin() {
        return plugin;
    }

    public HashMap<Player, Boolean> getIsPlaying() {
        return isPlaying;
    }

    public GUIManager getGuiManager() {
        return guiManager;
    }

    public void setIsPlaying(Player player, boolean isPlaying){
        this.isPlaying.put(player, isPlaying);
    }

    public boolean isPlaying(Player player){
        return isPlaying.get(player);
    }

    public void registerCommands(){
        plugin.getCommand("aimlab").setExecutor(new AimLabCommand(plugin, configManager, pointsManager, this));
    }
    public void registerEvents(){
        org.bukkit.plugin.PluginManager pluginManager = plugin.getServer().getPluginManager();
        pluginManager.registerEvents(new InventoryClickListener(plugin, configManager, pointsManager), plugin);
        pluginManager.registerEvents(new PlayerInteractListener(configManager, pointsManager), plugin);
        pluginManager.registerEvents(new PlayerJoinListener(pointsManager), plugin);
    }

    public ConfigManager getConfigManager() {
        return configManager;
    }

    public PointsManager getPointsManager() {
        return pointsManager;
    }

    public PointsDB getPointsDB() {
        return pointsDB;
    }
}
