package me.frxq15.boostersx.manager;

import me.frxq15.boostersx.BoostersX;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class LocaleManager {
    private final BoostersX plugin;

    public LocaleManager(BoostersX plugin) {
        this.plugin = plugin;
    }
    public void sendMessage(CommandSender player, String path) {
        player.sendMessage(plugin.colourize(plugin.getConfig().getString("MESSAGES." + path)));
    }
    public void sendRawMessage(CommandSender player, String message) {
        player.sendMessage(plugin.colourize(message));
    }
    public void sendListMessage(CommandSender player, String path) {
        plugin.getConfig().getStringList("MESSAGES." + path).forEach(line -> {
            player.sendMessage(plugin.colourize(line));
        });
    }
    public void broadcastMessage(String path) {
        Bukkit.broadcastMessage(plugin.colourize(plugin.getConfig().getString("MESSAGES." + path)));
    }
    public void broadcastListMessage(CommandSender player, String path) {
        plugin.getConfig().getStringList("MESSAGES." + path).forEach(line -> {
            Bukkit.broadcastMessage(plugin.colourize(line));
        });
    }
}
