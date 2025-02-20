package me.frxq15.boostersx.menu.menus;

import me.frxq15.boostersx.BoostersX;
import me.frxq15.boostersx.menu.GUITemplate;
import me.frxq15.boostersx.menu.ItemUtils;
import me.frxq15.boostersx.object.GPlayer;
import me.frxq15.boostersx.object.PlayerBoost;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.util.*;
import java.util.stream.Collectors;

public class ActivationMenu extends GUITemplate {
    private BoostersX plugin;
    private ItemUtils itemUtils;
    private UUID uuid;
    private GPlayer gPlayer;
    private Player player;
    private FileConfiguration config;
    private List<Integer> confirmSlots = new ArrayList<>();
    private List<Integer> cancelSlots = new ArrayList<>();
    private List<PlayerBoost> boosters;
    private int boosterSlot;
    private PlayerBoost boost;
    private int currentPage = 0;

    public ActivationMenu(BoostersX plugin, Player player, UUID uuid, PlayerBoost boost) {
        super(plugin, plugin.getFileManager().getMenusFile().getInt("ACTIVATION.rows"), plugin.getFileManager().getMenusFile().getString("ACTIVATION.title"));
        this.plugin = plugin;
        this.itemUtils = plugin.getItemUtils();
        this.uuid = uuid;
        this.gPlayer = plugin.getDataFactory().getFactory().getGPlayerData(uuid);
        this.player = player;
        this.boost = boost;
        this.config = plugin.getFileManager().getMenusFile();
        this.boosters = new ArrayList<>(gPlayer.getBoosters());
        this.confirmSlots = config.getStringList("ACTIVATION.items.confirm.slots").stream().map(Integer::parseInt).collect(Collectors.toList());
        this.cancelSlots = config.getStringList("ACTIVATION.items.cancel.slots").stream().map(Integer::parseInt).collect(Collectors.toList());
        this.boosterSlot = config.getInt("ACTIVATION.booster-slot");
        initialize();
    }

    public void initialize() {
        Map<String, String> replaceables = new HashMap<>();
        replaceables.put("%player%", gPlayer.getName());
        replaceables.put("%duration%", boost.getDurationFormatted());

        confirmSlots.forEach(slot -> setItem(slot, itemUtils.createMenuItem("ACTIVATION", "confirm", replaceables, gPlayer.getPlayer()),
                action -> {
            player.getOpenInventory().close();
            boost.getBooster().getActivationMessage().forEach(line -> {
                player.sendMessage(plugin.colourize(line));
            });
        }));
        cancelSlots.forEach(slot -> setItem(slot, itemUtils.createMenuItem("ACTIVATION", "cancel", replaceables, gPlayer.getPlayer()),
                action -> {
                    player.getOpenInventory().close();
                    new PersonalBoostersMenu(plugin, player, uuid).open(player);
                }));

        setItem(boosterSlot, boost.getBoosterItem());
    }
}
