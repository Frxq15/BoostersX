package me.frxq15.boostersx;

import me.frxq15.boostersx.command.CommandHandler;
import me.frxq15.boostersx.datafactory.sql.SQLManager;
import me.frxq15.boostersx.helper.BoostsHelper;
import me.frxq15.boostersx.manager.FileManager;
import me.frxq15.boostersx.manager.LocaleManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

public final class BoostersX extends JavaPlugin {
    public static BoostersX instance;
    private SQLManager sqlManager;
    private FileManager fileManager;
    private LocaleManager localeManager;
    private BoostsHelper boostsHelper;
    private CommandHandler commandHandler;

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
        localeManager = new LocaleManager(this);
        fileManager = new FileManager(this);
        fileManager.generate();
        sqlManager = new SQLManager(this);
        boostsHelper = new BoostsHelper(this);
        boostsHelper.cacheAllBoosters();
        commandHandler = new CommandHandler(this);
        commandHandler.load();
    }
    public SQLManager getSQLManager() {
        return sqlManager;
    }
    public FileManager getFileManager() {
        return fileManager;
    }
    public BoostsHelper getBoostsHelper() {
        return boostsHelper;
    }
    public LocaleManager getLocaleManager() {
        return localeManager;
    }
}
