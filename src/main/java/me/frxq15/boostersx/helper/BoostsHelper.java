package me.frxq15.boostersx.helper;

import me.frxq15.boostersx.BoostersX;
import me.frxq15.boostersx.object.Booster;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.configuration.file.FileConfiguration;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class BoostsHelper {
    private final BoostersX plugin;
    private List<Booster> boosters = new ArrayList<>();

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
}

