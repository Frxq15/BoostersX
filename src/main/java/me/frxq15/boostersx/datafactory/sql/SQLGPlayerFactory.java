package me.frxq15.boostersx.datafactory.sql;

import me.frxq15.boostersx.BoostersX;
import me.frxq15.boostersx.datafactory.DataFactory;
import me.frxq15.boostersx.object.Booster;
import me.frxq15.boostersx.object.GPlayer;
import me.frxq15.boostersx.object.PlayerBoost;
import org.bukkit.Bukkit;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
import java.util.stream.Collectors;

public class SQLGPlayerFactory implements DataFactory {
    private final static Map<UUID, GPlayer> players = new HashMap<>();
    private final BoostersX plugin;
    private final SQLManager sqlHandler;
    private final String PLAYERS_TABLE = "boostersx_players";
    private int savingTask;

    public SQLGPlayerFactory(BoostersX plugin, SQLManager sqlManager) {
        this.plugin = plugin;
        this.sqlHandler = sqlManager;
    }


    @Override
    public boolean initialize() {
        if (!sqlHandler.isConnected() && !sqlHandler.connect()) {
            plugin.log("Database: Can't establish a database connection!");
            return false;
        }

        try (PreparedStatement statement = sqlHandler.getConnection().prepareStatement("CREATE TABLE IF NOT EXISTS " + PLAYERS_TABLE + " " +
                "(uuid VARCHAR(36) PRIMARY KEY, name VARCHAR(16), boosters LONGTEXT, active_boosters LONGTEXT);")) {
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }

        //savingTask = startSavingTask();
        return true;
    }

    @Override
    public void terminate() {
        Bukkit.getScheduler().cancelTask(savingTask);
        savingTask = 0;

        if (!sqlHandler.isConnected() && !sqlHandler.connect()) {
            plugin.log("Database: Can't establish a database connection!");
            return;
        }

        for(GPlayer gPlayer : players.values()) {
            //updateGPlayerData(gPlayer);
        }
        players.clear();
    }

    @Override
    public void updatePlayerName(UUID uuid, String name) {
        try {
            PreparedStatement statement = sqlHandler.getConnection().prepareStatement("SELECT * FROM `" + PLAYERS_TABLE + "` WHERE uuid = ?;");
            statement.setString(1, uuid.toString());
            ResultSet result = statement.executeQuery();

            if (result.next() && !result.getString("player").equals(name)) {
                PreparedStatement update = sqlHandler.getConnection().prepareStatement("UPDATE `"+PLAYERS_TABLE + "` SET name = ? WHERE uuid = ?;");
                update.setString(1, name);
                update.setString(2, uuid.toString());
                update.executeUpdate();
            }
            result.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initializePlayerData(GPlayer gPlayer) {
        if (!sqlHandler.isConnected() && !sqlHandler.connect()) {
            plugin.log("Can't establish a database connection!");
            return;
        }
        if (doesPlayerExist(gPlayer.getUUID())) return;
        try {
            PreparedStatement statement = sqlHandler.getConnection().prepareStatement("INSERT INTO `" + PLAYERS_TABLE + "` (uuid, name, boosters, active_boosters) VALUES (?, ?, ?, ?);");
            statement.setString(1, gPlayer.getUUID().toString());
            statement.setString(2, gPlayer.getName());
            statement.setString(3, "");
            statement.setString(4, "");
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if (!players.containsKey(gPlayer.getUUID()))
            players.put(gPlayer.getUUID(), gPlayer);
    }

    @Override
    public GPlayer getGPlayerData(UUID uuid) {
        if (uuid == null) return null;

        if (players.get(uuid) != null)
            return players.get(uuid);

        if (!sqlHandler.isConnected() && !sqlHandler.connect()) {
            plugin.log("Database: Can't establish a database connection!");
            return null;
        }
        try (PreparedStatement statement = sqlHandler.getConnection().prepareStatement("SELECT * FROM " + PLAYERS_TABLE + " WHERE uuid=?")) {
            statement.setString(1, uuid.toString());

            ResultSet result = statement.executeQuery();
            GPlayer gPlayer = null;

            if (result.next()) {
                String stringUUID = result.getString("uuid");
                UUID uuidDB = (stringUUID == null ? null : UUID.fromString(stringUUID));
                String name = result.getString("name");
                String boosters = result.getString("boosters");
                String activeBoosters = result.getString("active_boosters");

                gPlayer = new GPlayer(plugin, uuidDB, name);

                if (!players.containsKey(gPlayer.getUUID()))
                    players.put(gPlayer.getUUID(), gPlayer);
            }

            result.close();
            return gPlayer;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void updateGPlayerData(GPlayer gPlayer) {

    }

    @Override
    public boolean doesPlayerExist(UUID uuid) {
        if (!sqlHandler.isConnected() && !sqlHandler.connect()) {
            plugin.log("Can't establish a database connection!");
            return false;
        }

        // Not checking cache as we want to check whether the data exists in the database (?)

        try (PreparedStatement statement = sqlHandler.getConnection().prepareStatement("SELECT uuid FROM `" + PLAYERS_TABLE + "` where uuid=?")) {
            statement.setString(1, uuid.toString());
            ResultSet result = statement.executeQuery();
            boolean exists = result.next();
            result.close();
            return exists;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean isPlayerDataLoaded(UUID uuid) {
        return players.containsKey(uuid);
    }

    @Override
    public void loadPlayerData(UUID uuid) {

    }

    @Override
    public void unloadPlayerData(UUID uuid) {

    }

    @Override
    public Map<UUID, GPlayer> getPlayers() {
        return players;
    }

    @Override
    public List<Booster> getBoosters(UUID uuid) {
        return List.of();
    }

    @Override
    public List<PlayerBoost> getActiveBoosts(UUID uuid) {
        return List.of();
    }

    @Override
    public String serializeBoosts(List<PlayerBoost> boosts) {
        if (boosts == null || boosts.isEmpty()) {
            return "";
        }
        return boosts.stream()
                .map(boost -> boost.getBooster().getID() + " " + boost.getDuration())
                .collect(Collectors.joining(":"));
    }
    public void saveBoosts(GPlayer gPlayer) {
        List<PlayerBoost> playerBoosts = gPlayer.getBoosters();

        // Serialize boosts as "type id duration:id duration etc.."
        String boostsString = playerBoosts.stream()
                .map(boost -> boost.getBooster().getID() + " " + boost.getDuration())
                .collect(Collectors.joining(":"));

        try (PreparedStatement statement = sqlHandler.getConnection().prepareStatement(
                "UPDATE `" + PLAYERS_TABLE + "` SET boosters=? WHERE uuid=?")) {
            statement.setString(1, boostsString);
            statement.setString(2, gPlayer.getUUID().toString());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<PlayerBoost> loadBoosts(GPlayer gPlayer) {
        List<PlayerBoost> loadedBoosts = new ArrayList<>();

        try (PreparedStatement statement = sqlHandler.getConnection().prepareStatement(
                "SELECT boosters FROM `" + PLAYERS_TABLE + "` WHERE uuid=?")) {
            statement.setString(1, gPlayer.getUUID().toString());
            ResultSet result = statement.executeQuery();

            if (result.next()) {
                String boostsString = result.getString("boosters");
                result.close();

                if (boostsString != null && !boostsString.isEmpty()) {
                    // Deserialize "type level:type level" into a list of PlayerBoosts
                    loadedBoosts = Arrays.stream(boostsString.split(":"))
                            .map(entry -> {
                                String[] parts = entry.split(" ");
                                String id = parts[0];
                                long duration = Long.parseLong(parts[1]);

                                Booster booster = plugin.getBoostsHelper().getBooster(id);

                                return booster != null ? new PlayerBoost(booster, duration) : null;
                            })
                            .filter(Objects::nonNull)
                            .collect(Collectors.toList());
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return loadedBoosts;
    }

    @Override
    public int startSavingTask() {
        return 0;
    }
}
