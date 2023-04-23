package com.zennyel.gui;

import com.zennyel.Z_AimLab;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

public class GameDurationGUI extends CustomGUI{


    public GameDurationGUI(Inventory inventory, FileConfiguration config, Z_AimLab instance) {
        super(inventory, config, instance);
    }

    public void addItems(){
        addGlasses();
        Item(Material.GREEN_SHULKER_BOX, "§a§l60 Seconds", "§7Click to select", 11);
        Item(Material.YELLOW_SHULKER_BOX, "§e§l90 Seconds", "§7Click to select", 13);
        Item(Material.RED_SHULKER_BOX, "§c§l120 Seconds", "§7Click to select", 15);
    }


    public void addGlasses(){
        for(int i = 0; i < getInventory().getSize(); i++) {
            Item(Material.GRAY_STAINED_GLASS_PANE, "*", "", i);
        }
    }



}
