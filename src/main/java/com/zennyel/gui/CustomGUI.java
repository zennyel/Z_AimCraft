package com.zennyel.gui;

import com.zennyel.Z_AimLab;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;


public abstract class CustomGUI implements Listener {

    private Inventory inventory;
    private FileConfiguration config;
    private Z_AimLab instance;

    public CustomGUI(Inventory inventory, FileConfiguration config, Z_AimLab instance) {
        this.instance = instance;
        this.inventory = inventory;
        this.config = config;
    }

    public void addItems() {

    }

    public ItemStack Item(Material material, String displayName, String description, int slotPosition) {
        ItemStack item = new ItemStack(material);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(displayName);
        ArrayList<String> lore = new ArrayList<String>();
        lore.add(description);
        meta.setLore(lore);
        item.setItemMeta(meta);
        getInventory().setItem(slotPosition, item);
        return null;
    }

    public ItemStack Item(Material material, String displayName, List<String> description, int slotPosition) {
        ItemStack item = new ItemStack(material);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(displayName);
        meta.setLore(description);
        item.setItemMeta(meta);
        getInventory().setItem(slotPosition, item);
        return null;
    }

    public Inventory getInventory() {
        return inventory;
    }

    public FileConfiguration getConfig() {
        return config;
    }

    public Z_AimLab getInstance() {
        return instance;
    }

}
