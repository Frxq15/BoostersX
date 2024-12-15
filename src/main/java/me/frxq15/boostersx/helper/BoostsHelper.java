package me.frxq15.boostersx.helper;

import me.frxq15.boostersx.BoostersX;
import me.frxq15.boostersx.object.Booster;

import java.util.*;

import me.frxq15.boostersx.object.PlayerBoost;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;
import org.bukkit.entity.Player;

public class BoostsHelper {
    private final BoostersX plugin;
    private List<Booster> boosters = new ArrayList<>();
    private final Map<UUID, List<PlayerBoost>> activeBoosts = new ConcurrentHashMap<>();

    public BoostsHelper(BoostersX plugin) {
        this.plugin = plugin;
        cacheAllBoosters();
    }

    // Cache all boosters from config
    public void cacheAllBoosters() {
        boosters.clear();
        FileConfiguration config = plugin.getConfig();

        if (config.isConfigurationSection("boosts")) {
            Set<String> keys = config.getConfigurationSection("boosts").getKeys(false);

            for (String key : keys) {
                if (config.isConfigurationSection("boosts." + key)) {
                    Set<String> boostKeys = config.getConfigurationSection("boosts." + key).getKeys(false);

                    for (String boostKey : boostKeys) {
                        String path = "boosts." + key + "." + boostKey;
                        String id = key + "_" + boostKey;
                        double multiplier = config.getDouble(path + ".multiplier.boost", 1.0);
                        List<String> currencies = config.getStringList(path + ".multiplier.enchants");
                        String plugin_boost = config.getString(path + ".plugin");

                        Booster booster = new Booster(id, multiplier, currencies, plugin_boost);
                        cacheBooster(booster);
                    }
                }
            }
        }
    }

    public List<Booster> getBoosters() {
        return boosters;
    }
    public void cacheBooster(Booster booster) {
        boosters.add(booster);
    }
    public Booster getBoosterById(String id) {
        return boosters.stream()
                .filter(booster -> booster.getID().equalsIgnoreCase(id))
                .findFirst()
                .orElse(null);
    }
    public List<Booster> getBoostersByPlugin(String plugin) {
        return boosters.stream()
                .filter(booster -> booster.getPluginBoost().equalsIgnoreCase(plugin))
                .collect(Collectors.toList());
    }
    public void reloadCache() {
        cacheAllBoosters();
    }
    public void addPlayerBoost(UUID playerUUID, PlayerBoost boost) {
        activeBoosts.computeIfAbsent(playerUUID, k -> new ArrayList<>()).add(boost);
    }
    public void removePlayerBoost(UUID playerUUID, PlayerBoost boost) {
        List<PlayerBoost> boosts = activeBoosts.get(playerUUID);
        if (boosts != null) {
            boosts.remove(boost);
            if (boosts.isEmpty()) {
                activeBoosts.remove(playerUUID);
            }
        }
    }

    public List<PlayerBoost> getActiveBoosts(UUID playerUUID) {
        List<PlayerBoost> boosts = activeBoosts.get(playerUUID);
        if (boosts != null) {
            boosts.removeIf(PlayerBoost::isExpired);
            if (boosts.isEmpty()) {
                activeBoosts.remove(playerUUID);
            }
        }
        return boosts != null ? boosts : Collections.emptyList();
    }

    private void startExpiryCheckTask() {
        Bukkit.getScheduler().runTaskTimer(plugin, () -> {
            for (UUID playerUUID : new HashSet<>(activeBoosts.keySet())) {
                List<PlayerBoost> boosts = activeBoosts.get(playerUUID);
                if (boosts != null) {
                    boosts.removeIf(boost -> {
                        boolean expired = boost.isExpired();
                        if (expired) {
                            notifyPlayerBoostExpired(playerUUID, boost);
                        }
                        return expired;
                    });
                    if (boosts.isEmpty()) {
                        activeBoosts.remove(playerUUID);
                    }
                }
            }
        }, 0L, 20 * 60L); // Runs every 60 seconds
    }

    // Notify player when a boost expires
    private void notifyPlayerBoostExpired(UUID playerUUID, PlayerBoost boost) {
        Player player = Bukkit.getPlayer(playerUUID);
        if (player != null && player.isOnline()) {
            player.sendMessage("Your boost " + boost.getBooster().getID() + " has expired.");
        }
    }

    // Clear all active boosts (e.g., on reset or reload)
    public void clearAllActiveBoosts() {
        activeBoosts.clear();
    }
}

