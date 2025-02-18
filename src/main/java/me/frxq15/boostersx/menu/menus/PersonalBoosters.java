package me.frxq15.boostersx.menu.menus;

import me.frxq15.boostersx.BoostersX;
import me.frxq15.boostersx.menu.GUITemplate;
import me.frxq15.boostersx.menu.ItemUtils;
import me.frxq15.boostersx.object.GPlayer;
import me.frxq15.boostersx.object.PlayerBoost;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.*;
import java.util.stream.Collectors;

public class PersonalBoosters extends GUITemplate {
    private BoostersX plugin;
    private ItemUtils itemUtils;
    private UUID uuid;
    private GPlayer gPlayer;
    private FileConfiguration config;
    private List<Integer> boosterSlots = new ArrayList<>();
    private List<PlayerBoost> boosters;
    private int currentPage = 0;

    public PersonalBoosters(BoostersX plugin, UUID uuid) {
        super(plugin, plugin.getFileManager().getMenusFile().getInt("PERSONAL.rows"), plugin.getFileManager().getMenusFile().getString("PERSONAL.title"));
        this.plugin = plugin;
        this.itemUtils = plugin.getItemUtils();
        this.uuid = uuid;
        this.gPlayer = plugin.getDataFactory().getFactory().getGPlayerData(uuid);
        this.config = plugin.getFileManager().getMenusFile();
        this.boosters = new ArrayList<>(gPlayer.getBoosters());
        this.boosterSlots = config.getStringList("PERSONAL.booster-slots").stream().map(Integer::parseInt).collect(Collectors.toList());
        initialize();
    }

    public void initialize() {
        updatePage();
    }

    private void updatePage() {
        clearInventory();

        Map<String, String> replaceables = new HashMap<>();
        replaceables.put("%player%", gPlayer.getName());

        config.getConfigurationSection("PERSONAL.items").getKeys(false).forEach(key -> {
            setItem(getSlot("PERSONAL", key), itemUtils.createMenuItem("PERSONAL", key, replaceables, gPlayer.getPlayer()));
        });

        config.getStringList("PERSONAL.panes.slots").forEach(slot -> {
            setItem(Integer.parseInt(slot), itemUtils.createPaneItem("PERSONAL"));
        });

        int boostersPerPage = boosterSlots.size();
        int totalPages = (int) Math.ceil((double) boosters.size() / boostersPerPage);

        if (currentPage >= totalPages) {
            currentPage = Math.max(0, totalPages - 1);
        }

        int startIndex = currentPage * boostersPerPage;
        int endIndex = Math.min(startIndex + boostersPerPage, boosters.size());

        for (int i = startIndex, slotIndex = 0; i < endIndex; i++, slotIndex++) {
            PlayerBoost boost = boosters.get(i);
            ItemStack boostItem = boost.getBoosterItem();
            setItem(boosterSlots.get(slotIndex), boostItem);
        }

        if (currentPage > 0) {
            setItem(getPageSlot("PERSONAL", "previous-page"), itemUtils.createPageItem("PERSONAL", "previous-page"), event -> {
                currentPage--;
                updatePage();
            });
        }
        if (currentPage < totalPages - 1) {
            setItem(getPageSlot("PERSONAL", "next-page"), itemUtils.createPageItem("PERSONAL", "next-page"), event -> {
                currentPage++;
                updatePage();
            });
        }
    }

}

