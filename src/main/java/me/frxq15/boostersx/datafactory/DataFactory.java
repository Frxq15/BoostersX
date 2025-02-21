package me.frxq15.boostersx.datafactory;

import me.frxq15.boostersx.object.Booster;
import me.frxq15.boostersx.object.GPlayer;
import me.frxq15.boostersx.object.PlayerBoost;

import java.util.List;
import java.util.Map;
import java.util.UUID;

public interface DataFactory {

    // The main interface for the data factory
    boolean initialize();
    void terminate();
    int startSavingTask();

    // Player methods
    void updatePlayerName(UUID uuid, String name);
    void initializePlayerData(GPlayer gPlayer);
    GPlayer getGPlayerData(UUID uuid);
    void updateGPlayerData(GPlayer gPlayer);
    boolean doesPlayerExist(UUID uuid);
    boolean isPlayerDataLoaded(UUID uuid);
    void loadPlayerData(UUID uuid);
    void unloadPlayerData(UUID uuid);
    Map<UUID, GPlayer> getPlayers();
    void unloadPlayerDataAsync(UUID uuid);

    // Booster methods
    List<PlayerBoost> deserializeActiveBoosters(String boostsString);

    String serializeBoosters(List<PlayerBoost> boosts);
    String serializeActiveBoosters(List<PlayerBoost> boosts);
    void saveBoosters(GPlayer gPlayer);
    List<PlayerBoost> deserializeBoosters(String boostsString);
}
