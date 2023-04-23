package com.zennyel.listeners;

import com.zennyel.Z_AimLab;
import com.zennyel.game.Difficulty;
import com.zennyel.gui.GameDifficultyGUI;
import com.zennyel.gui.GameDurationGUI;
import com.zennyel.gui.GameStartingGUI;
import com.zennyel.managers.ConfigManager;
import com.zennyel.managers.GUIManager;
import com.zennyel.managers.GameManager;
import com.zennyel.managers.PointsManager;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

import java.util.ArrayList;
import java.util.List;

public class InventoryClickListener implements Listener {

    private GUIManager guiManager;
    private final ConfigManager configManager;
    private Z_AimLab instance;
    private final PointsManager pointsManager;
    private Difficulty difficulty = Difficulty.NORMAL;
    private Long duration = 60L;

    public InventoryClickListener(Z_AimLab instance, ConfigManager configManager, PointsManager pointsManager) {
        this.guiManager = new GUIManager(pointsManager, instance.getPluginManager().getConfigManager(), instance);
        this.instance = instance;
        this.configManager = configManager;
        this.pointsManager = pointsManager;
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event){
        Player player = (Player) event.getWhoClicked();
        handleGameStartingGUI(event, player);
        handleGameDurationGUI(event, player);
        handleGameDifficultyGUI(event, player);
        handleStatisticsGUI(event, player);
    }

    public void handleStatisticsGUI(InventoryClickEvent event, Player player){
        String statisticsTitle = guiManager.getInventoryHolder().getStatisticsTitle();
        GameStartingGUI gameStartingGUI = guiManager.getGameStartingGUI();
        gameStartingGUI.addItems();
        if(event.getView().getTitle().equalsIgnoreCase(statisticsTitle)){
            event.setCancelled(true);
            switch (event.getRawSlot()){
                case 11:
                    player.openInventory(gameStartingGUI.getInventory());
                    break;
                case 15:
                    List<String> lore = new ArrayList<>();
                    lore.add("§7Your Game Statistics");
                    lore.add("");
                    lore.add("§aPoints: " + pointsManager.getPoints(player));
                    int hits = pointsManager.getPoints(player) / 20;
                    lore.add("§aCertain Hits: " + hits);
                    lore.add("§c");
                    for(String s : lore) {
                        player.sendMessage(s);
                    }
                    break;
            }
        }
    }

    public void handleGameDifficultyGUI(InventoryClickEvent event, Player player){
        String gameDifficultyTitle = guiManager.getInventoryHolder().getGameDifficultyTitle();
        GameStartingGUI gameStartingGUI = guiManager.getGameStartingGUI();
        gameStartingGUI.addItems();
        if (event.getView().getTitle().equalsIgnoreCase(gameDifficultyTitle)) {
            switch (event.getRawSlot()) {
                case 10:
                    setDifficulty(Difficulty.EASY);
                    break;
                case 12:
                    setDifficulty(Difficulty.NORMAL);
                    break;
                case 14:
                    setDifficulty(Difficulty.MEDIUM);
                    break;
                case 16:
                    setDifficulty(Difficulty.HARD);
                    break;
            }
            playSound(player);
            player.openInventory(gameStartingGUI.getInventory());
        }
    }

    public void handleGameDurationGUI(InventoryClickEvent event, Player player){
        String gameDurationTitle = guiManager.getInventoryHolder().getGameDurationTitle();
        GameStartingGUI gameStartingGUI = guiManager.getGameStartingGUI();
        gameStartingGUI.addItems();
        if(event.getView().getTitle().equalsIgnoreCase(gameDurationTitle)){
            event.setCancelled(true);
            switch (event.getRawSlot()){
                case 11:
                    setDuration(60L);
                    break;
                case 13:
                    setDuration(90L);
                    break;
                case 15:
                    setDuration(120L);
                    break;
            }
            playSound(player);
            player.openInventory(gameStartingGUI.getInventory());
        }
    }

    public void handleGameStartingGUI(InventoryClickEvent event, Player player){
        String gameStartingTitle = guiManager.getInventoryHolder().getGameStartingTitle();
        if(event.getView().getTitle().equalsIgnoreCase(gameStartingTitle)){
            event.setCancelled(true);
            switch (event.getRawSlot()){
                case 11:
                    GameDurationGUI gameDurationGUI = guiManager.getGameDurationGUI();
                    gameDurationGUI.addItems();
                    player.openInventory(gameDurationGUI.getInventory());
                    break;
                case 13:
                    GameManager manager = new GameManager(configManager, instance, pointsManager, guiManager);
                    manager.startGame(player, getDifficulty(), getDuration());
                    player.sendMessage("§aGame Started!");
                    break;
                case 15:
                    GameDifficultyGUI gameDifficultyGUI = guiManager.getGameDifficultyGUI();
                    gameDifficultyGUI.addItems();
                    player.openInventory(gameDifficultyGUI.getInventory());
                    break;
            }
            playSound(player);
        }
    }

    public void playSound(Player player){
        player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_BASS, 1, 1);
    }

    public Difficulty getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(Difficulty difficulty) {
        this.difficulty = difficulty;
    }

    public Long getDuration() {
        return duration;
    }

    public void setDuration(Long duration) {
        this.duration = duration;
    }
}
