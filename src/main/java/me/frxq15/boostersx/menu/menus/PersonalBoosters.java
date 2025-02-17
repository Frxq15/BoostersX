package me.frxq15.boostersx.menu.menus;

import me.frxq15.boostersx.BoostersX;
import me.frxq15.boostersx.menu.GUITemplate;
import me.frxq15.boostersx.object.GPlayer;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class PersonalBoosters extends GUITemplate {
    private BoostersX plugin;
    private UUID uuid;
    private GPlayer gPlayer;
    private FileConfiguration config;

    public PersonalBoosters(BoostersX plugin, UUID uuid) {
        super(plugin, plugin.getFileManager().getMenusFile().getInt("PERSONAL.rows"), plugin.getFileManager().getMenusFile().getString("PERSONAL.title"));
        this.plugin = plugin;
        this.uuid = uuid;
        this.gPlayer = plugin.getDataFactory().getFactory().getGPlayerData(uuid);
        this.config = plugin.getFileManager().getMenusFile();
        initialize();
    }
    public void initialize() {
        Map<String, String> replaceables = new HashMap<>();
        replaceables.put("%player%", gPlayer.getName());

        config.getConfigurationSection("PERSONAL.items").getKeys(false).forEach(key -> {
            setItem(getSlot("PERSONAL", key), plugin.getItemUtils().createMenuItem("PERSONAL", key, replaceables, gPlayer.getPlayer()));
        });
    }
}
