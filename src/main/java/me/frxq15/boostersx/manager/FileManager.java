package me.frxq15.boostersx.manager;

import me.frxq15.boostersx.BoostersX;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class FileManager {
    private final BoostersX plugin;
    public File BoostersFile;
    public FileConfiguration BoostersConfig;

    public FileManager(BoostersX plugin) {
        this.plugin = plugin;
    }

    public void generate() {
        createBoostersFile();
    }
    public void createBoostersFile() {
        BoostersFile = new File(plugin.getDataFolder(), "boosters.yml");
        if (!BoostersFile.exists()) {
            BoostersFile.getParentFile().mkdirs();
            plugin.log("File: boosters.yml was created successfully");
            plugin.saveResource("boosters.yml", false);
        }

        BoostersConfig = new YamlConfiguration();
        try {
            BoostersConfig.load(BoostersFile);
        } catch (IOException | InvalidConfigurationException e) {
            e.printStackTrace();
        }
    }
    public void reloadBoostersFile() { BoostersConfig = YamlConfiguration.loadConfiguration(BoostersFile); }
    public void saveBoostersFile() {
        try {
            BoostersConfig.save(BoostersFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public FileConfiguration getBoostersFile() { return BoostersConfig; }
}
