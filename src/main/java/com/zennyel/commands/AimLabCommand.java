package com.zennyel.commands;

import com.zennyel.Z_AimLab;
import com.zennyel.gui.GameStartingGUI;
import com.zennyel.gui.StatisticGUI;
import com.zennyel.managers.*;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import org.jetbrains.annotations.NotNull;

public class AimLabCommand implements CommandExecutor {
    private Z_AimLab instance;
    private ConfigManager configManager;
    private GUIManager guiManager;
    private PointsManager pointsManager;
    private final PluginManager pluginManager;


    public AimLabCommand(Z_AimLab instance, ConfigManager configManager, PointsManager pointsManager, PluginManager pluginManager) {
        this.instance = instance;
        this.configManager = configManager;
        this.pointsManager = pointsManager;
        guiManager = new GUIManager(pointsManager, instance.getPluginManager().getConfigManager(), instance);
        this.pluginManager = pluginManager;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
       if(!(commandSender instanceof Player)){
           System.out.println("§cThis command can only be used by players!");
       }
       Player player = (Player) commandSender;

        if(strings.length == 0){
            player.sendMessage(
                    "\n§cIncorrect usage. Here are the available options:\n" +
                            "§c/aimlab menu - Displays the game menu.\n" +
                            "§c/aimlab setPos - Sets the game location.\n" +
                            "§c/aimlab points - Shows you best pontuation.\n" +
                            "§c/aimlab stop - Stops the game, if currently playing.\n" +
                            "§c/aimlab statistics - Open the statistics menu\n\n"
            );
            return false;
        }

        if(strings.length == 1){
            if(!player.hasPermission("aimlab.use")) {
                player.sendMessage("§cYou don't have pemission to do this command.");
                return false;
            }
        }

        switch (strings[0]){
            case "menu":
                GameStartingGUI gameStartingGUI =  guiManager.getGameStartingGUI();
                gameStartingGUI.addItems();
                player.openInventory(gameStartingGUI.getInventory());
                break;
            case "setpos":
                if(!player.hasPermission("aimlab.admin.use")) {
                    player.sendMessage("§cYou don't have permission to use this command.");
                    return false;
                }
                    Location playerLoc = player.getLocation();
                    configManager.setConfigLocation(playerLoc);
                    player.sendMessage(String.format("§aPosition successfully set, X: %.1f, Y: %.1f, Z: %.1f",
                            playerLoc.getX(), playerLoc.getY(), playerLoc.getZ()));
                    return true;
            case "points":
                int points = pointsManager.getPoints(player);
                player.sendMessage("§aYou best pontuation is: " + points);
                break;
            case "stop":
                GameManager gameManager = new GameManager(configManager, instance, pointsManager, guiManager);
                    gameManager.stopGame(player);
                if(!GameManager.getIsPlaying(player)){
                    return false;
                }
                    player.sendMessage("§cGame stopped!");
                    break;
            case "statistics":
                StatisticGUI statisticGUI = guiManager.getStatisticGUI();
                statisticGUI.addItems(player);
                player.openInventory(statisticGUI.getInventory());
        }



        return false;
    }
}
