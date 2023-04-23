package com.zennyel.managers;

import org.bukkit.Bukkit;
import org.bukkit.inventory.Inventory;

public class InventoryHolder {
    private final Inventory gameDifficulty;
    private final Inventory gameStarting;
    private final Inventory gameDuration;
    private final Inventory gameRunning;
    private final Inventory statistics;

    private final String gameDifficultyTitle = "§6§l§nGame Difficulty";
    private final String gameStartingTitle = "§6§l§nGame Starting";
    private final String gameDurationTitle = "§6§l§nGame duration";
    private final String gameRunningTitle = "§6§l§nGame Running";
    private final String statisticsTitle = "§6§l§nStatistics";

    public InventoryHolder() {
        statistics = Bukkit.createInventory(null, 27, statisticsTitle);
        gameDifficulty = Bukkit.createInventory(null, 27, gameDifficultyTitle);
        gameStarting = Bukkit.createInventory(null, 27, gameStartingTitle);
        gameDuration = Bukkit.createInventory(null, 27, gameDurationTitle);
        gameRunning = Bukkit.createInventory(null, 27, gameRunningTitle);
    }

    public String getGameDifficultyTitle() {
        return gameDifficultyTitle;
    }

    public String getGameStartingTitle() {
        return gameStartingTitle;
    }

    public String getGameDurationTitle() {
        return gameDurationTitle;
    }

    public String getGameRunningTitle() {
        return gameRunningTitle;
    }

    public Inventory getStatistics() {
        return statistics;
    }

    public String getStatisticsTitle() {
        return statisticsTitle;
    }

    public Inventory getGameDifficulty() {
        return gameDifficulty;
    }

    public Inventory getGameStarting() {
        return gameStarting;
    }

    public Inventory getGameDuration() {
        return gameDuration;
    }

    public Inventory getGameRunning() {
        return gameRunning;
    }
}




