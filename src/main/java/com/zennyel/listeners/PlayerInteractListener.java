package com.zennyel.listeners;

import com.zennyel.managers.ConfigManager;
import com.zennyel.managers.PointsManager;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class PlayerInteractListener implements Listener {

    private final ConfigManager configManager;
    private final PointsManager pointsManager;

    public PlayerInteractListener(ConfigManager configManager, PointsManager pointsManager) {
        this.configManager = configManager;
        this.pointsManager = pointsManager;
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event){
        Player player = event.getPlayer();
        Block block = event.getClickedBlock();
        ItemStack aimBlock = configManager.getItemStack();
        if(block == null){
            return;
        }
        if (block.getType() == aimBlock.getType()) {
            Location location = player.getLocation();
            location.getWorld().playSound(location, Sound.ENTITY_PLAYER_HURT, 1.0f, 1.0f);
            location.getWorld().spawnParticle(Particle.CRIT_MAGIC, location, 10, 0.5, 0.5, 0.5);
            block.setType(Material.AIR);
            player.sendActionBar("§6§l+20");
            pointsManager.addPoints(player, 20);

        }
    }

}
