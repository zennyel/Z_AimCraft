package com.zennyel.gui;

import com.zennyel.Z_AimLab;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import java.util.ArrayList;
import java.util.List;

public class GameStartingGUI extends CustomGUI{

    public GameStartingGUI(Inventory inventory, FileConfiguration config, Z_AimLab instance) {
        super(inventory, config, instance);
    }

    public void addItems(){
        addGlasses();
        Item(Material.BLACK_SHULKER_BOX, "§6§lChoose Difficulty", "§7Click to open difficulty menu", 15);
        List<String> lore = new ArrayList<>();
        lore.add("§7Click to start the game");
        lore.add("");
        lore.add("§cIf you don't choose any difficulty or");
        lore.add("§cgame time, it will start the default");
        lore.add("§cdifficulty and duration");
        Item(Material.COMPASS, "§6§lStart Game", lore,  13);
        Item(Material.GRAY_SHULKER_BOX, "§6§lChoose Duration", "§7Click to open the duration menu", 11);
    }

    public void addGlasses(){
        for(int i = 0; i < getInventory().getSize(); i++) {
            Item(Material.GRAY_STAINED_GLASS_PANE, "*", "", i);
        }
    }

}
