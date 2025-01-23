package me.frxq15.boostersx.datafactory.yml;

import me.frxq15.boostersx.BoostersX;
import me.frxq15.boostersx.datafactory.DataFactory;
import me.frxq15.boostersx.object.Booster;
import me.frxq15.boostersx.object.GPlayer;
import me.frxq15.boostersx.object.PlayerBoost;

import java.util.List;
import java.util.Map;
import java.util.UUID;

public class YMLGPlayerFactory implements DataFactory {

    private final BoostersX plugin;

    public YMLGPlayerFactory(BoostersX plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean initialize() {
        return false;
    }

    @Override
    public void terminate() {

    }

    @Override
    public int startSavingTask() {
        return 0;
    }

    @Override
    public void updatePlayerName(UUID uuid, String name) {

    }

    @Override
    public void initializePlayerData(GPlayer gPlayer) {

    }

    @Override
    public void getGPlayerData(UUID uuid) {

    }

    @Override
    public void updateGPlayerData(GPlayer gPlayer) {

    }

    @Override
    public boolean doesPlayerExist(UUID uuid) {
        return false;
    }

    @Override
    public boolean isPlayerDataLoaded(UUID uuid) {
        return false;
    }

    @Override
    public void loadPlayerData(UUID uuid) {

    }

    @Override
    public void unloadPlayerData(UUID uuid) {

    }

    @Override
    public Map<UUID, GPlayer> getPlayers() {
        return Map.of();
    }

    @Override
    public List<Booster> getBoosters(UUID uuid) {
        return List.of();
    }

    @Override
    public List<PlayerBoost> getActiveBoosts(UUID uuid) {
        return List.of();
    }
}
