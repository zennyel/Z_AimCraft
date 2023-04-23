package com.zennyel.game;

import com.zennyel.Z_AimLab;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.Random;

public class AimLab {

    private int points;
    private Location location;
    private ItemStack itemStack;
    private Difficulty difficulty;
    private Z_AimLab instance;

    public AimLab(Location location, ItemStack itemStack, Difficulty difficulty, Z_AimLab instance) {
        this.location = location;
        this.itemStack = itemStack;
        this.difficulty = difficulty;
        this.instance = instance;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public void setItemStack(ItemStack itemStack) {
        this.itemStack = itemStack;
    }

    public void setDifficulty(Difficulty difficulty) {
        this.difficulty = difficulty;
    }

    public int getPoints() {
        return points;
    }

    public Location getLocation() {
        return location;
    }

    public ItemStack getItemStack() {
        return itemStack;
    }

    public Difficulty getDifficulty() {
        return difficulty;
    }

    public void spawnMovingBlock(long frequency, Location playerLoc) {
        int x = playerLoc.getBlockX() + 2;
        int y = playerLoc.getBlockY();
        int z = playerLoc.getBlockZ() - 2;

        if (x > playerLoc.getWorld().getMaxHeight() - 3) {
            x = playerLoc.getWorld().getMaxHeight() - 3;
        }

        if (z > playerLoc.getWorld().getMaxHeight() - 3) {
            z = playerLoc.getWorld().getLogicalHeight() - 3;
        }

        Random rand = new Random();
        World world = playerLoc.getWorld();

        int randX = rand.nextInt(2) + x;
        int randY = rand.nextInt(3) + y;
        int randZ = rand.nextInt(3) + z;

        Block block = world.getBlockAt(randX, randY, randZ);

        if (block.getType() == Material.AIR) {
            block.setType(itemStack.getType());
            Bukkit.getScheduler().runTaskLater(instance, () -> block.setType(Material.AIR), frequency);
        }
    }




}
