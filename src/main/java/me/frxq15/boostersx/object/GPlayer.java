package me.frxq15.boostersx.object;

import java.util.*;
import java.util.stream.Collectors;

public class GPlayer {
    private final UUID uuid;
    private List<PlayerBoost> boosters = new ArrayList<>();
    private final static Map<UUID, GPlayer> players = new HashMap<>();

    public GPlayer(UUID uuid) {
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
}
