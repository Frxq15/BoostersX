package me.frxq15.boostersx.menu;

import me.frxq15.boostersx.BoostersX;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.inventory.ItemStack;

import java.util.Map;

public class ItemUtils {
    private final BoostersX plugin;
    private FileConfiguration config;

    public ItemUtils(BoostersX plugin) {
        this.plugin = plugin;
        this.config = plugin.getFileManager().getMenusFile();
    }
    public ItemStack createMenuItem(String menu, String item, Map<String, String> replaceables) {
        String name = config.getString(menu + ".items." + item + ".name");
        ItemStack itemStack = new ItemStack(Material.valueOf(config.getString(menu + ".items." + item + ".material")), config.getInt(menu + ".items." + item + ".amount"));

        return null;
    }
}
