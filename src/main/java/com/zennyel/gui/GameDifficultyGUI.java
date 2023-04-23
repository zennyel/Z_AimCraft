package com.zennyel.gui;

import com.zennyel.Z_AimLab;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

public class GameDifficultyGUI extends CustomGUI{


    public GameDifficultyGUI(Inventory inventory, FileConfiguration config, Z_AimLab instance) {
        super(inventory, config, instance);
    }

    public void addItems(){
        addGlasses();
        Item(Material.WHITE_SHULKER_BOX, "§f§lEasy", "§7Click to select", 10);
        Item(Material.GREEN_SHULKER_BOX, "§a§lNormal", "§7Click to select", 12);
        Item(Material.YELLOW_SHULKER_BOX, "§e§lMedium", "§7Click to select", 14);
        Item(Material.RED_SHULKER_BOX, "§c§lHard", "§7Click to select", 16);
    }


    public void addGlasses(){
        for(int i = 0; i < getInventory().getSize(); i++) {
            Item(Material.GRAY_STAINED_GLASS_PANE, "*", "", i);
        }
    }


}
