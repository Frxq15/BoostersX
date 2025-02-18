package me.frxq15.boostersx.menu;

import me.frxq15.boostersx.BoostersX;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ItemUtils {
    private final BoostersX plugin;
    private FileConfiguration config;

    public ItemUtils(BoostersX plugin) {
        this.plugin = plugin;
        this.config = plugin.getFileManager().getMenusFile();
    }
    public ItemStack createMenuItem(String menu, String item, Map<String, String> replaceables, OfflinePlayer player) {
        List<String> lore = new ArrayList<>();
        String name = config.getString(menu + ".items." + item + ".name");
        Material material = Material.valueOf(config.getString(menu + ".items." + item + ".material"));
        int amount = config.getInt(menu + ".items." + item + ".amount");

        ItemStack itemStack = new ItemStack(material, amount);
        ItemMeta meta = itemStack.getItemMeta();

        for (Map.Entry<String, String> entry : replaceables.entrySet()) {
            name = name.replace(entry.getKey(), entry.getValue());
        }
        meta.setDisplayName(plugin.colourize(name));

        for (String line : config.getStringList(menu + ".items." + item + ".lore")) {
            for (Map.Entry<String, String> entry : replaceables.entrySet()) {
                line = line.replace(entry.getKey(), entry.getValue());
            }
            lore.add(plugin.colourize(line));
        }
        meta.setLore(lore);

        if (meta instanceof LeatherArmorMeta) {
            LeatherArmorMeta leatherMeta = (LeatherArmorMeta) meta;

            String colorString = config.getString(menu + ".items." + item + ".rgb");
            if (colorString != null) {
                String[] rgb = colorString.split(",");
                if (rgb.length == 3) {
                    try {
                        int r = Integer.parseInt(rgb[0]);
                        int g = Integer.parseInt(rgb[1]);
                        int b = Integer.parseInt(rgb[2]);
                        leatherMeta.setColor(Color.fromRGB(r, g, b));
                    } catch (NumberFormatException e) {
                        plugin.log("Error: Invalid color format provided in menus.yml for " + item);
                    }
                }
            }

            itemStack.setItemMeta(leatherMeta);
        }
        boolean glow = config.getBoolean(menu + ".items." + item + ".glow");
        if(glow) {
            meta.addEnchant(Enchantment.LUCK, 1, true);
            meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
            itemStack.setItemMeta(meta);
        }

        else if (meta instanceof SkullMeta && player != null) {
            SkullMeta skullMeta = (SkullMeta) meta;
            skullMeta.setOwningPlayer(player);
            itemStack.setItemMeta(skullMeta);
        }

        else {
            itemStack.setItemMeta(meta);
        }

        return itemStack;
    }
    public ItemStack createPaneItem(String menu) {
        Material material = Material.valueOf(config.getString(menu + ".panes.material"));
        return new ItemStack(material, 1);
    }
    public ItemStack createPageItem(String menu, String item) {
        Material material = Material.valueOf(config.getString(menu + ".page-items."+item+".material"));
        int amount = config.getInt(menu + ".page-items."+item+".amount");
        String name = config.getString(menu + ".page-items."+item+".name");
        List<String> lore = config.getStringList(menu + ".page-items."+item+".lore");
        ItemStack itemStack = new ItemStack(material, amount);
        ItemMeta meta = itemStack.getItemMeta();
        meta.setDisplayName(plugin.colourize(name));
        List<String> loreList = new ArrayList<>();
        for (String line : lore) {
            loreList.add(plugin.colourize(line));
        }
        meta.setLore(loreList);
        itemStack.setItemMeta(meta);
        return itemStack;
    }
}
