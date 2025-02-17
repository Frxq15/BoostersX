package me.frxq15.boostersx.object;

import me.frxq15.boostersx.BoostersX;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.*;
import java.util.stream.Collectors;

public class GPlayer {
    private final BoostersX plugin;
    private final UUID uuid;
    private List<PlayerBoost> boosters = new ArrayList<>();
    private List<PlayerBoost> active_boosters = new ArrayList<>();
    public final static Map<UUID, GPlayer> players = new HashMap<>();

    private String name;
    private int boosterExpiryCheck = -1;

    public GPlayer(BoostersX plugin, UUID uuid, String name, List<PlayerBoost> boosters, List<PlayerBoost> active_boosters) {
        this.plugin = plugin;
        this.uuid = uuid;
        this.name = name;
        setBoosters(boosters);
        setActiveBoosters(active_boosters);
        players.put(uuid, this);
    }

    public GPlayer(BoostersX plugin, UUID uuid, String name) {
        this.plugin = plugin;
        this.uuid = uuid;
        this.name = name;
        this.boosters = new ArrayList<>();
        this.active_boosters = new ArrayList<>();
        players.put(uuid, this);
    }


    public UUID getUUID() {
        return uuid;
    }

    //booster methods

    public void setBoosters(List<PlayerBoost> boosters) {
        this.boosters = boosters;
    }
    public void setActiveBoosters(List<PlayerBoost> boosters) {
        this.active_boosters = boosters;
    }

    public List<PlayerBoost> getBoosters() {
        return boosters;
    }
    public void activateBooster(PlayerBoost booster) {
        active_boosters.add(booster);
        boosters.remove(booster);
        booster.activate(System.currentTimeMillis());
    }
    public List<PlayerBoost> getActiveBoosters() {
        return active_boosters.stream()
                .filter(booster -> !booster.isExpired())
                .collect(Collectors.toList());
    }
    public void addBooster(PlayerBoost booster) {
        boosters.add(booster);
    }
    public PlayerBoost findFirstByIdAndDuration(String boosterId, long duration) {
        for (PlayerBoost playerBoost : getBoosters()) {
            if (playerBoost.getBooster().getID().equalsIgnoreCase(boosterId) && playerBoost.getDuration() == duration) {
                return playerBoost;
            }
        }
        return null;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public Player getPlayer() {
        if(Bukkit.getPlayer(uuid) != null) {
            return Bukkit.getPlayer(uuid);
        }
        return null;
    }

    public void startExpiryCheckTask() {
        if (boosterExpiryCheck != -1) {
            return;
        }
        boosterExpiryCheck = Bukkit.getScheduler().runTaskTimerAsynchronously(plugin, () -> {
            Iterator<PlayerBoost> iterator = active_boosters.iterator();
            while (iterator.hasNext()) {
                PlayerBoost boost = iterator.next();
                if (boost.isExpired()) {
                    Bukkit.getScheduler().runTask(plugin, () -> {
                        boost.getBooster().getDeactivatedMessage().forEach(message -> plugin.getLocaleManager().sendRawMessage(getPlayer(), message));
                    });
                    iterator.remove();
                }
            }
        }, 0L, 20 * 60L).getTaskId();
    }


    public void stopExpiryCheckTask() {
        if (boosterExpiryCheck != -1) {
            Bukkit.getScheduler().cancelTask(boosterExpiryCheck);
            boosterExpiryCheck = -1;
        }
    }
    public boolean isOnline() {
        return getPlayer() != null && getPlayer().isOnline();
    }
}
