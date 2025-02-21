package me.frxq15.boostersx.util;

import me.frxq15.boostersx.BoostersX;
import me.frxq15.boostersx.datafactory.sql.SQLGPlayerFactory;
import me.frxq15.boostersx.datafactory.sql.SQLListeners;
import me.frxq15.boostersx.enums.StorageType;
import org.bukkit.Bukkit;

public class Settings {
    private final BoostersX plugin;
    private StorageType storageType;
    private SQLGPlayerFactory SQLGPlayerFactory;

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
                if(plugin.getSQLManager().connect()) {
                    this.SQLGPlayerFactory = new SQLGPlayerFactory(plugin, plugin.getSQLManager());
                    SQLGPlayerFactory.initialize();
                    plugin.log("Database: Successfully connected to database!");
                    Bukkit.getPluginManager().registerEvents(new SQLListeners(plugin), plugin);
                    break;
                }
                plugin.log("Settings: Storage type MYSQL was specified but could not connect to database.");
            case "yml":
                this.storageType = StorageType.YML;
                break;
            default:
                plugin.log("Settings: An error occured while processing the storage type - using yml as default.");
                this.storageType = StorageType.YML;
        }
    }
    public StorageType getStorageType() {
        return storageType;
    }
}
