package com.zennyel.managers;

import com.zennyel.Z_AimLab;
import com.zennyel.game.AimLab;
import com.zennyel.game.Difficulty;
import com.zennyel.gui.StatisticGUI;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.block.BlockFace;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class GameManager {

    private ConfigManager configManager;
    private Z_AimLab instance;
    private List<BossBar> bossBars;
    private static HashMap<Player, List<BossBar>> playerBossBars = new HashMap<>();
    private static HashMap<Player,Integer> playerMbTask = new HashMap<>();
    private static HashMap<Player,Boolean> playerTpTask = new HashMap<>();
    private static HashMap<Player, Boolean> isPlaying = new HashMap<>();
    private final PointsManager pointsManager;
    private AimLab aimLab;
    private final GUIManager guiManager;

    public GameManager(ConfigManager configManager, Z_AimLab instance, PointsManager pointsManager, GUIManager guiManager) {
        this.configManager = configManager;
        this.instance = instance;
        this.pointsManager = pointsManager;
        this.guiManager = guiManager;
        bossBars = new ArrayList<>();
    }
    public void stopGame(Player player){
        if(!getIsPlaying(player)){
            player.sendMessage("§cYou already not in a game!");
            return;
        }
        setIsPlaying(player, false);
        player.setWalkSpeed(0.25F);
        player.setFlySpeed(0.25F);
        for(BossBar bossBar : playerBossBars.get(player)){
            bossBar.setVisible(false);
            bossBar.removeAll();
            playerBossBars.put(player, null);
        }
        Bukkit.getScheduler().cancelTask(playerMbTask.get(player));
        playerMbTask.put(player, null);
        playerTpTask.put(player, false);
        StatisticGUI statisticGUI = guiManager.getStatisticGUI();
        statisticGUI.addItems(player);
        player.openInventory(statisticGUI.getInventory());
    }
    public void stopGame(Player player, Location previousLocation){
        if(!getIsPlaying(player)){
            player.sendMessage("§cYou already not in a game!");
            return;
        }
        setIsPlaying(player, false);
        player.setWalkSpeed(0.25F);
        player.setFlySpeed(0.25F);
        for(BossBar bossBar : playerBossBars.get(player)){
            bossBar.setVisible(false);
            bossBar.removeAll();
            playerBossBars.put(player, null);
        }
        if(playerTpTask.get(player)){
            player.teleport(previousLocation);
        }
        player.teleport(previousLocation);
        Bukkit.getScheduler().cancelTask(playerMbTask.get(player));
        playerMbTask.put(player, null);
        playerTpTask.put(player, false);
        StatisticGUI statisticGUI = guiManager.getStatisticGUI();
        statisticGUI.addItems(player);
        player.openInventory(statisticGUI.getInventory());
    }

    int taskId;
    int taskId2;

    public void startGame(Player player, Difficulty difficulty, Long duration){

        Location location = configManager.getConfigLocation();
        Location previousLocation = player.getLocation();
        Bukkit.getScheduler().runTaskLater(instance, ()-> {
            stopGame(player, previousLocation);
            if(playerMbTask != null){
                Bukkit.getScheduler().cancelTask(playerMbTask.get(player));
                playerMbTask.put(player, null);
            }
        }, duration * 20);
        player.teleport(location.setDirection(BlockFace.EAST.getDirection()));
        setIsPlaying(player, true);
        ItemStack itemStack = configManager.getItemStack();
        this.aimLab = new AimLab(location, itemStack, difficulty, instance);
        createBossBar(player, duration);
        player.setWalkSpeed(0);
        player.setFlying(false);
        taskId = Bukkit.getScheduler().runTaskTimer(instance, () -> {
            if(getIsPlaying(player)) {
                for (int i = 0; i < getLoops(difficulty); i++) {
                    aimLab.spawnMovingBlock(getTicks(difficulty), location);
                }
            }
        }, 0L, getTicks(difficulty)).getTaskId();
        playerMbTask.put(player, taskId);
        playerTpTask.put(player, true);
    }

    private long getTicks(Difficulty difficulty){
        switch (difficulty){
            case EASY:
                return 20L;
            case NORMAL:
                return 15L;
            case MEDIUM:
                return 10L;
            case HARD:
                return 8L;
        }
        return 0;
    }

    private int getLoops(Difficulty difficulty){
        switch (difficulty){
            case EASY:
                return 1;
            case MEDIUM:
                return 3;
            case NORMAL:
                return 2;
            case HARD:
                return 4;
        }
        return 0;
    }

    private void createBossBar(Player player, Long seconds) {
        BossBar bossBar = Bukkit.createBossBar("§b§lTime Remaining: " + seconds + "s", BarColor.BLUE, BarStyle.SOLID);
        bossBar.setColor(BarColor.BLUE);
        bossBar.addPlayer(player);
        bossBar.setVisible(true);
        bossBars.add(bossBar);
        playerBossBars.put(player, bossBars);

        new BukkitRunnable() {
            Long remainingSeconds = seconds;

            @Override
            public void run() {
                bossBar.setProgress((double) remainingSeconds / seconds);

                if (remainingSeconds == 0) {
                    bossBar.setVisible(false);
                    bossBar.removeAll();
                    cancel();
                } else {
                    bossBar.setTitle("§b§lTime Remaining: " + remainingSeconds + "s");
                    remainingSeconds--;
                }
            }
        }.runTaskTimer(instance, 0L, 20L);
    }

    public static void setIsPlaying(Player player, boolean isPlaying){
        GameManager.isPlaying.put(player, isPlaying);
    }

    public static Boolean getIsPlaying(Player player){
        return GameManager.isPlaying.get(player);
    }

}
