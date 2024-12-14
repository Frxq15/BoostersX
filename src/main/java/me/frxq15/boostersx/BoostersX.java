package me.frxq15.boostersx;

import me.frxq15.boostersx.datafactory.sql.SQLManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

public final class BoostersX extends JavaPlugin {
    public static BoostersX instance;
    private SQLManager sqlManager;

    @Override
    public void onEnable() {
        instance = this;
        registry();
        log("Plugin Enabled Successfully.");
    }

    @Override
    public void onDisable() {
        log("Plugin Disabled Successfully.");
    }
    public static BoostersX getInstance() {
        return instance;
    }
    public void log(String message) {
        Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "[BoostersX] " + message);
    }
    public String colourize(String message) {
        return ChatColor.translateAlternateColorCodes('&', message);
    }
    public void registry() {
        saveDefaultConfig();
        sqlManager = new SQLManager(this);
    }
    public SQLManager getSQLManager() {
        return sqlManager;
    }
}
