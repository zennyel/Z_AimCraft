package com.zennyel.gui;

import com.zennyel.Z_AimLab;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

public class GameRunningGUI extends CustomGUI{


    public GameRunningGUI(Inventory inventory, FileConfiguration config, Z_AimLab instance) {
        super(inventory, config, instance);
    }

    public void addItems(){
        addGlasses();
        Item(Material.RED_SHULKER_BOX, "§c§lQuit Game", "§7Click to exit game.", 12);
        Item(Material.GREEN_SHULKER_BOX, "§a§lContinue Game", "§7Continue the game.", 14);
    }



    public void addGlasses(){
        for(int i = 0; i < getInventory().getSize(); i++) {
            Item(Material.GRAY_STAINED_GLASS_PANE, "*", "", i);
        }
    }
}
