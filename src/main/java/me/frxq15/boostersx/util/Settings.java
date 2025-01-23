package me.frxq15.boostersx.util;

import me.frxq15.boostersx.BoostersX;
import me.frxq15.boostersx.enums.StorageType;

public class Settings {
    private final BoostersX plugin;
    private StorageType storageType;

    public Settings(BoostersX plugin) {
        this.plugin = plugin;
    }
    public void initialize() {
        assignStorageType();
    }
    public void assignStorageType() {
        switch(plugin.getConfig().getString("storage-type")) {
            case "mysql":
                this.storageType = StorageType.MYSQL;
                plugin.getSQLManager().connect();
                break;
            case "yml":
                this.storageType = StorageType.YML;
                break;
            default:
                plugin.log("Settings:Invalid storage type in config.yml! Valid options are 'mysql' or 'yml' - using yml as default.");
                this.storageType = StorageType.YML;
        }
    }
    public StorageType getStorageType() {
        return storageType;
    }
}
