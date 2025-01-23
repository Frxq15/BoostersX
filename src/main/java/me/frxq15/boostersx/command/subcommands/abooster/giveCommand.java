package me.frxq15.boostersx.command.subcommands.abooster;

import me.frxq15.boostersx.BoostersX;
import me.frxq15.boostersx.object.Booster;
import me.frxq15.boostersx.command.SubCommand;
import me.frxq15.boostersx.object.GPlayer;
import me.frxq15.boostersx.object.PlayerBoost;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.List;

public class giveCommand extends SubCommand {
    private final BoostersX plugin;

    public giveCommand(BoostersX plugin) {
        super("info", "boostersx.command.admin.give", "/abooster give <player> <id> <duration>", Arrays.asList("add"));
        this.plugin = plugin;
    }

    @Override
    public @NotNull void onCommand(@NotNull CommandSender s, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if(args.length == 3) {
            OfflinePlayer target = Bukkit.getOfflinePlayer(args[0]);
            if(!GPlayer.players.containsKey(target.getUniqueId())) {
                plugin.getLocaleManager().sendMessage(s, "PLAYER_NOT_FOUND");
                return;
            }
            String targetBooster = args[1];
            if(!plugin.getBoostsHelper().getBoosters().stream().map(Booster::getID).toList().contains(targetBooster)) {
                plugin.getLocaleManager().sendMessage(s, "BOOSTER_NOT_FOUND");
                return;
            }
            //validate duration later

            Booster booster = plugin.getBoostsHelper().getBooster(targetBooster);
            PlayerBoost boost = new PlayerBoost(booster, 120000L); //default as 2 mins for testing

        }
    }

    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if(args.length == 1) {
            return getVisiblePlayers(sender);
        }
        if(args.length == 2) {
            return plugin.getBoostsHelper().getBoosters().stream().map(Booster::getID).toList();
        }
        if (args.length == 3) {
            return List.of("1m", "1h", "1d");
        }
        return List.of();
    }
}
