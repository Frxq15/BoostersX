package me.frxq15.boostersx.helper;

import me.frxq15.boostersx.BoostersX;
import me.frxq15.boostersx.object.Booster;

import java.util.ArrayList;
import java.util.List;

public class BoostsHelper {
    private final BoostersX plugin;
    private List<Booster> boosters = new ArrayList<>();

    public BoostsHelper(BoostersX plugin) {
        this.plugin = plugin;
        cacheAllBoosters();
    }
    public void cacheAllBoosters() {
        
    }
    public List<Booster> getBoosters() {
        return boosters;
    }
    public void cacheBooster(Booster booster) {
        boosters.add(booster);
    }
}
