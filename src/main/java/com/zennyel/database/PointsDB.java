package com.zennyel.database;

import com.zennyel.Z_AimLab;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.sql.*;
import java.util.*;

public class PointsDB extends SQLite{

    public PointsDB(Z_AimLab instance) {
        super(instance);
    }

    public void createPointsTable() {
        String sql = "CREATE TABLE IF NOT EXISTS `points_db` (`id` VARCHAR(36), `points` INT)";
        try (Connection conn = getConnection(); PreparedStatement stm = conn.prepareStatement(sql)) {
            stm.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<String> getLeaderBoard() {
        List<String> leaderboard = new ArrayList<>();
        leaderboard.add("§e§l=-=-=-=-=--=-=-=-=-=-=-=-=-=-=-=");

        String sql = "SELECT `id`, `points` FROM `points_db`";
        Map<String, Integer> playerPoints = new HashMap<>();
        try (Connection conn = getConnection(); PreparedStatement stm = conn.prepareStatement(sql)) {
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                String playerId = rs.getString("id");
                int points = rs.getInt("points");
                playerPoints.put(playerId, points);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        List<Map.Entry<String, Integer>> sortedPlayers = new ArrayList<>(playerPoints.entrySet());
        sortedPlayers.sort(Map.Entry.comparingByValue(Comparator.reverseOrder()));

        int rank = 1;
        for (Map.Entry<String, Integer> entry : sortedPlayers) {
            Player player = Bukkit.getPlayer(UUID.fromString(entry.getKey()));
            if (player != null) {
                String playerName = player.getName();
                int playerScore = entry.getValue();
                leaderboard.add("§e§l" + rank + ". " + playerName + ": " + playerScore);
                rank++;
                if (rank > 5) {
                    break;
                }
            }
            leaderboard.add("§e§l=-=-=-=-=--=-=-=-=-=-=-=-=-=-=-=");
        }

        return leaderboard;
    }




    public int getPlayerPoints(Player player) {
        String sql = "SELECT `points` FROM `points_db` WHERE `id` = ?";
        try (Connection conn = getConnection(); PreparedStatement stm = conn.prepareStatement(sql)) {
            stm.setString(1, player.getUniqueId().toString());
            ResultSet rs = stm.executeQuery();
            if (rs.next()) {
                return rs.getInt("points");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public void insertPlayerPoints(Player player, int points) {
        String sql = "INSERT INTO points_db (id, points) VALUES (?, ?);";
        try (Connection conn = getConnection(); PreparedStatement stm = conn.prepareStatement(sql)) {
            stm.setString(1, player.getUniqueId().toString());
            stm.setInt(2, points);
            stm.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }




    public Connection getConnection(){
        return getConnection("database.db");
    }

}
