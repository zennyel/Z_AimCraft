package com.zennyel;


import com.zennyel.managers.GameManager;
import com.zennyel.managers.PluginManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public final class Z_AimLab extends JavaPlugin {

    private PluginManager pluginManager;

    @Override
    public void onEnable() {
        this.pluginManager = new PluginManager(this);
        pluginManager.registerEvents();
        pluginManager.registerCommands();
        saveDefaultConfig();
        Bukkit.getConsoleSender().sendMessage(StartMessage());
        for(Player p : Bukkit.getOnlinePlayers()) {
            pluginManager.getPointsManager().cachePoints(p);
            GameManager.setIsPlaying(p, false);
        }
    }

    @Override
    public void onDisable(){
        for(Player player: Bukkit.getOnlinePlayers()) {
            GameManager gameManager = new GameManager(getPluginManager().getConfigManager(), this, getPluginManager().getPointsManager(), getPluginManager().getGuiManager());
            gameManager.stopGame(player);
            pluginManager.getPointsManager().loadPoints(player);
        }
    }

    public String StartMessage(){
        return "\n\n§5Plugin Z_AimCraft Starting! \nVersion 2.0-Final \n";
    }

    public PluginManager getPluginManager() {
        return pluginManager;
    }
}
