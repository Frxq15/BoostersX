package me.frxq15.boostersx.object;

import me.frxq15.boostersx.BoostersX;

import java.util.*;
import java.util.stream.Collectors;

public class GPlayer {
    private final BoostersX plugin;
    private final UUID uuid;
    private List<PlayerBoost> boosters = new ArrayList<>();
    private List<PlayerBoost> active_boosters = new ArrayList<>();
    public final static Map<UUID, GPlayer> players = new HashMap<>();

    private String name;

    public GPlayer(BoostersX plugin, UUID uuid, String name, List<PlayerBoost> boosters, List<PlayerBoost> active_boosters) {
        this.plugin = plugin;
        this.uuid = uuid;
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
    public List<PlayerBoost> getActiveBoosters() {
        return active_boosters.stream()
                .filter(booster -> !booster.isExpired())
                .collect(Collectors.toList());
    }
    public void addBooster(PlayerBoost booster) {
        boosters.add(booster);
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
}
