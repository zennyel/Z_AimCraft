package com.zennyel.gui;

import com.zennyel.Z_AimLab;
import com.zennyel.managers.ConfigManager;
import com.zennyel.managers.PointsManager;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import java.util.ArrayList;
import java.util.List;

public class StatisticGUI extends CustomGUI{

    private final ConfigManager configManager;
    private final PointsManager pointsManager;

    public StatisticGUI(Inventory inventory, FileConfiguration config, Z_AimLab instance, ConfigManager configManager, PointsManager pointsManager) {
        super(inventory, config, instance);
        this.configManager = configManager;
        this.pointsManager = pointsManager;
    }

    public void addItems(Player p){
        addGlasses();
        Item(Material.BLACK_SHULKER_BOX, "§6§lBack to Menu", "§7Click to open the main menu", 11);
        List<String> lore = new ArrayList<>();
        lore.add("§7Your Game Statistics");
        lore.add("");
        lore.add("§aPoints: " + pointsManager.getPoints(p));
        int hits = pointsManager.getPoints(p) / 20;
        lore.add("§aCertain Hits: " + hits);
        lore.add("§c");
        Item(configManager.getItemStack().getType(), "§6§l" + p.getName() + " Statistics", lore,  15);
    }

    public void addGlasses(){
        for(int i = 0; i < getInventory().getSize(); i++) {
            Item(Material.GRAY_STAINED_GLASS_PANE, "*", "", i);
        }
    }
}
