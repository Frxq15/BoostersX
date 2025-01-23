package me.frxq15.boostersx.object;

import me.frxq15.boostersx.BoostersX;

import java.util.*;
import java.util.stream.Collectors;

public class GPlayer {
    private final BoostersX plugin;
    private final UUID uuid;
    private List<PlayerBoost> boosters = new ArrayList<>();
    public final static Map<UUID, GPlayer> players = new HashMap<>();

    private String name;

    public GPlayer(BoostersX plugin, UUID uuid, String name) {
        this.plugin = plugin;
        this.uuid = uuid;
        players.put(uuid, this);
    }
    public UUID getUUID() {
        return uuid;
    }
    public List<PlayerBoost> getBoosters() {
        return boosters;
    }
    public List<PlayerBoost> getActiveBoosters() {
        return boosters.stream()
                .filter(booster -> !booster.isExpired())
                .collect(Collectors.toList());
    }
    public void addBooster(PlayerBoost booster) {
        boosters.add(booster);
    }
    public String getName() {
        return name;
    }
}
