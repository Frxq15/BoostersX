package me.frxq15.boostersx.datafactory.sql;

import me.frxq15.boostersx.BoostersX;
import me.frxq15.boostersx.object.GPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.UUID;

public class SQLListeners implements Listener {
    private final BoostersX plugin;

    public SQLListeners(BoostersX plugin) {
        this.plugin = plugin;
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onAsyncPlayerPreLoginEvent(AsyncPlayerPreLoginEvent event) {
        UUID uuid = event.getUniqueId();
        String name = event.getName();

        GPlayer gPlayer = plugin.getDataFactory().getFactory().getGPlayerData(uuid);
        if (gPlayer == null) {
            if (plugin.getDataFactory().getFactory().doesPlayerExist(uuid)) {
                event.setLoginResult(AsyncPlayerPreLoginEvent.Result.KICK_OTHER);
                event.setKickMessage("An error occurred, please contact an admininstrator");
                return;
            }

            gPlayer = new GPlayer(plugin, uuid, name);
            gPlayer.startExpiryCheckTask();
            plugin.getDataFactory().getFactory().updatePlayerName(uuid, name);
            plugin.getDataFactory().getFactory().initializePlayerData(gPlayer);
        } else {
            gPlayer.setName(name);
        }
    }
    @EventHandler(priority = EventPriority.MONITOR)
    public void onPlayerQuitEvent(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        GPlayer gPlayer = plugin.getDataFactory().getFactory().getGPlayerData(player.getUniqueId());
        gPlayer.stopExpiryCheckTask();
        plugin.getDataFactory().getFactory().unloadPlayerDataAsync(gPlayer.getUUID());
    }
}
