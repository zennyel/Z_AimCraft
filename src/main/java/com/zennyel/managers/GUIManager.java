package com.zennyel.managers;

import com.zennyel.Z_AimLab;
import com.zennyel.gui.*;

public class GUIManager {

    private GameDifficultyGUI gameDifficultyGUI;
    private GameDurationGUI gameDurationGUI;
    private GameRunningGUI gameRunningGUI;
    private GameStartingGUI gameStartingGUI;
    private StatisticGUI statisticGUI;

    private InventoryHolder inventoryHolder;
    private final PointsManager pointsManager;
    private ConfigManager configManager;
    private Z_AimLab instance;

    public GUIManager(PointsManager pointsManager, ConfigManager configManager, Z_AimLab instance) {
        this.pointsManager = pointsManager;
        this.configManager = configManager;
        this.instance = instance;
        this.inventoryHolder = new InventoryHolder();
        this.statisticGUI = new StatisticGUI(inventoryHolder.getStatistics(), configManager.getFileConfiguration(), instance, configManager, pointsManager);
        this.gameDifficultyGUI = new GameDifficultyGUI(inventoryHolder.getGameDifficulty(), configManager.getFileConfiguration(), instance);
        this.gameDurationGUI = new GameDurationGUI(inventoryHolder.getGameDuration(), configManager.getFileConfiguration(), instance);
        this.gameRunningGUI = new GameRunningGUI(inventoryHolder.getGameRunning(), configManager.getFileConfiguration(), instance);
        this.gameStartingGUI = new GameStartingGUI(inventoryHolder.getGameStarting(), configManager.getFileConfiguration(), instance);
    }

    public StatisticGUI getStatisticGUI() {
        return statisticGUI;
    }

    public InventoryHolder getInventoryHolder() {
        return inventoryHolder;
    }

    public GameDifficultyGUI getGameDifficultyGUI() {
        return gameDifficultyGUI;
    }

    public GameDurationGUI getGameDurationGUI() {
        return gameDurationGUI;
    }

    public GameRunningGUI getGameRunningGUI() {
        return gameRunningGUI;
    }

    public GameStartingGUI getGameStartingGUI() {
        return gameStartingGUI;
    }
}
