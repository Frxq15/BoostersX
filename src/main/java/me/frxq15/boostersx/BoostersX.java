package me.frxq15.boostersx;

import me.frxq15.boostersx.command.CommandHandler;
import me.frxq15.boostersx.datafactory.DataFactoryHandler;
import me.frxq15.boostersx.datafactory.sql.SQLManager;
import me.frxq15.boostersx.helper.BoostsHelper;
import me.frxq15.boostersx.manager.FileManager;
import me.frxq15.boostersx.manager.LocaleManager;
import me.frxq15.boostersx.menu.GUIListeners;
import me.frxq15.boostersx.menu.ItemUtils;
import me.frxq15.boostersx.util.PlayerUtils;
import me.frxq15.boostersx.util.Settings;
import me.frxq15.boostersx.util.TimeUtils;
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
    private Settings settings;
    private DataFactoryHandler dataFactoryHandler;
    private TimeUtils timeUtils;
    private PlayerUtils playerUtils;
    private ItemUtils itemUtils;

    @Override
    public void onEnable() {
        instance = this;
        registry();
        log("Enabled " + getDescription().getName() + " v" + getDescription().getVersion());
    }

    @Override
    public void onDisable() {
        if(sqlManager.isConnected()) {
            getDataFactory().getFactory().terminate();
        }
        log("Disabled " + getDescription().getName() + " v" + getDescription().getVersion());
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

        settings = new Settings(this);
        settings.initialize();

        dataFactoryHandler = new DataFactoryHandler(this);

        boostsHelper = new BoostsHelper(this);
        boostsHelper.cacheAllBoosters();

        commandHandler = new CommandHandler(this);
        commandHandler.load();

        itemUtils = new ItemUtils(this);
        timeUtils = new TimeUtils(this);
        playerUtils = new PlayerUtils(this);

        Bukkit.getPluginManager().registerEvents(new GUIListeners(), this);


    }
    public SQLManager getSQLManager() {
        return sqlManager;
    }
    public FileManager getFileManager() {
        return fileManager;
    }
    public ItemUtils getItemUtils() {
        return itemUtils;
    }
    public BoostsHelper getBoostsHelper() {
        return boostsHelper;
    }
    public LocaleManager getLocaleManager() {
        return localeManager;
    }
    public Settings getSettings() {
        return settings;
    }
    public DataFactoryHandler getDataFactory() {
        return dataFactoryHandler;
    }
    public TimeUtils getTimeUtils() {
        return timeUtils;
    }
    public PlayerUtils getPlayerUtils() {
        return playerUtils;
    }
}
