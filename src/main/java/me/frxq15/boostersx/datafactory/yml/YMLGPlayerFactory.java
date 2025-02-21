package me.frxq15.boostersx.datafactory.yml;

import me.frxq15.boostersx.BoostersX;
import me.frxq15.boostersx.datafactory.DataFactory;
import me.frxq15.boostersx.object.GPlayer;
import me.frxq15.boostersx.object.PlayerBoost;
import org.bukkit.Bukkit;

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
    public GPlayer getGPlayerData(UUID uuid) {
        return null;
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
    public void unloadPlayerDataAsync(UUID uuid) {
        Bukkit.getScheduler().runTaskAsynchronously(plugin, () -> unloadPlayerData(uuid));
    }

    @Override
    public List<PlayerBoost> deserializeActiveBoosters(String boostsString) {
        return List.of();
    }

    @Override
    public String serializeBoosters(List<PlayerBoost> boosts) {
        return "";
    }
    @Override
    public String serializeActiveBoosters(List<PlayerBoost> boosts) {
        return "";
    }

    @Override
    public void saveBoosters(GPlayer gPlayer) {

    }
    @Override
    public List<PlayerBoost> deserializeBoosters(String boostsString) {
        return List.of();
    }
}
