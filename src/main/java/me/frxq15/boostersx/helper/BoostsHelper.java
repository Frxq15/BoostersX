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

    public BoostsHelper(BoostersX plugin) {
        this.plugin = plugin;
        cacheAllBoosters();
    }

    public void cacheAllBoosters() {
        boosters.clear();
        FileConfiguration config = plugin.getFileManager().getBoostersFile();

        if (config.isConfigurationSection("boosts")) {
            Set<String> keys = config.getConfigurationSection("boosts").getKeys(false);

            for (String key : keys) {
                String path = "boosts." + key;
                if (config.isConfigurationSection(path)) {
                    String displayName = config.getString(path + ".name", "Default Booster");
                    double multiplier = config.getDouble(path + ".multiplier.boost", 1.0);
                    List<String> enchants = config.getStringList(path + ".multiplier.enchants");
                    List<String> currencies = config.getStringList(path + ".multiplier.currencies");
                    List<String> lore = config.getStringList(path + ".lore");
                    List<String> activationMessage = config.getStringList(path + ".activation_message");
                    List<String> deactivatedMessage = config.getStringList(path + ".deactivated_message");
                    List<String> tooltip = config.getStringList(path + ".tooltip");

                    Booster booster = new Booster(key, multiplier, currencies, enchants, activationMessage, deactivatedMessage, displayName, lore, tooltip);
                    cacheBooster(booster);
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
    public void reloadCache() {
        cacheAllBoosters();
    }
    public Booster getBooster(String id) {
        return boosters.stream().filter(booster -> booster.getID().equals(id)).findFirst().orElse(null);
    }

    private void notifyPlayerBoostExpired(UUID playerUUID, PlayerBoost boost) {
        Player player = Bukkit.getPlayer(playerUUID);
        if (player != null && player.isOnline()) {
            player.sendMessage("Your boost " + boost.getBooster().getID() + " has expired.");
        }
    }
}

