package com.zennyel.managers;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import com.zennyel.Z_AimLab;
import org.bukkit.*;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Item;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.lang.reflect.Field;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Base64;
import java.util.UUID;

public class ConfigManager {

    private FileConfiguration fileConfiguration;
    private Location location;
    private ItemStack itemStack;
    private Z_AimLab instance;

    public ConfigManager(FileConfiguration fileConfiguration, Z_AimLab instance) {
        this.fileConfiguration = fileConfiguration;
        this.instance = instance;
        setLocation(getConfigLocation());
        this.itemStack = createItemStack();
    }

    public ItemStack getItemStack() {
        return this.createItemStack();
    }

    public ItemStack createItemStack(){
        return getSkullFromName(fileConfiguration.getString("Game.Item.Skull_Name"));
    }

    public ItemStack getSkullFromName(String playerName) {
        ItemStack item = new ItemStack(Material.PLAYER_HEAD);
        SkullMeta meta = (SkullMeta) item.getItemMeta();
        meta.setDisplayName(playerName + "'s head");
        GameProfile profile = new GameProfile(UUID.randomUUID(), playerName);
        byte[] encodedData = Base64.getEncoder().encode(String.format("{textures:{SKIN:{url:\"http://textures.minecraft.net/texture/%s\"}}}", getTexture(playerName)).getBytes());
        profile.getProperties().put("textures", new Property("textures", new String(encodedData)));
        Field profileField = null;
        try {
            profileField = meta.getClass().getDeclaredField("profile");
            profileField.setAccessible(true);
            profileField.set(meta, profile);
        } catch (NoSuchFieldException | IllegalArgumentException | IllegalAccessException e) {
            e.printStackTrace();
        }
        item.setItemMeta(meta);
        return item;
    }

    private String getTexture(String playerName) {
        try {
            URL url = new URL(String.format("https://api.mojang.com/users/profiles/minecraft/%s", playerName));
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder response = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
            reader.close();
            JsonObject jsonObject = new JsonParser().parse(response.toString()).getAsJsonObject();
            String uuid = jsonObject.get("id").getAsString();
            url = new URL(String.format("https://sessionserver.mojang.com/session/minecraft/profile/%s?unsigned=false", uuid));
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            response = new StringBuilder();
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
            reader.close();
            jsonObject = new JsonParser().parse(response.toString()).getAsJsonObject();
            JsonArray propertiesArray = jsonObject.get("properties").getAsJsonArray();
            for (JsonElement element : propertiesArray) {
                JsonObject propertyObject = element.getAsJsonObject();
                String name = propertyObject.get("name").getAsString();
                if (name.equals("textures")) {
                    return propertyObject.get("value").getAsString();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }







    public void setLocation(Location location){
        this.location = location;
    }

    public void setConfigLocation(Location location){
        FileConfiguration configuration = instance.getConfig();
        configuration.set("Game.Wolrd", location.getWorld());
        configuration.set("Game.Position.X", location.getBlockX());
        configuration.set("Game.Position.Y", location.getBlockY());
        configuration.set("Game.Position.Z", location.getBlockZ());
        instance.saveConfig();
    }

    public Location getConfigLocation(){
        int x = instance.getConfig().getInt("Game.Position.X");
        int y = instance.getConfig().getInt("Game.Position.Y");
        int z = instance.getConfig().getInt("Game.Position.Z");
        World world = Bukkit.getWorld(instance.getConfig().getString("Game.World"));
        final Location gameLocation = new Location(world, x,y,z);
        return gameLocation;
    }

    public Location getLocation(){
        return location;
    }

    public FileConfiguration getFileConfiguration() {
        return fileConfiguration;
    }
}
