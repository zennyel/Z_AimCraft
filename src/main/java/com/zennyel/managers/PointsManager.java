package com.zennyel.managers;

import com.zennyel.Z_AimLab;
import com.zennyel.database.PointsDB;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.HashMap;

public class PointsManager {

    private HashMap<Player, Integer> playerIntegerHashMap;
    private PointsDB db;
    private Z_AimLab plugin;

    public PointsManager(PointsDB db, Z_AimLab plugin) {
        this.db = db;
        this.plugin = plugin;
        playerIntegerHashMap = new HashMap<>();
    }

    public void addPoints(Player player, int points){
        setPoints(player, getPoints(player) + points);
    }

    public void setPoints(Player player, int points){
        this.playerIntegerHashMap.put(player, points);
    }

    public int getPoints(Player player){
        return  playerIntegerHashMap.get(player);
    }

    public void loadPoints(Player player){
            if(playerIntegerHashMap.get(player) != null) {
                db.insertPlayerPoints(player, playerIntegerHashMap.get(player));
            }
    }

    public void cachePoints(Player player){
        playerIntegerHashMap.put(player, db.getPlayerPoints(player));
    }

    public void cacheClear(Player player){
        playerIntegerHashMap.clear();
    }




}
