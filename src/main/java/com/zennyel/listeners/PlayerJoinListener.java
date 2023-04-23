package com.zennyel.listeners;

import com.zennyel.managers.GameManager;
import com.zennyel.managers.PointsManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerJoinListener implements Listener {

    private PointsManager pointsManager;

    public PlayerJoinListener(PointsManager pointsManager) {
        this.pointsManager = pointsManager;
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event){
        Player player = event.getPlayer();
        pointsManager.cachePoints(player);
        GameManager.setIsPlaying(player, false);
    }

    @EventHandler
    public void onLeave(PlayerQuitEvent event){
        Player player = event.getPlayer();
        pointsManager.loadPoints(player);
    }

}
