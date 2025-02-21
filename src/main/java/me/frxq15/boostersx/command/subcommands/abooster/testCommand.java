package me.frxq15.boostersx.command.subcommands.abooster;

import me.frxq15.boostersx.BoostersX;
import me.frxq15.boostersx.command.SubCommand;
import me.frxq15.boostersx.object.Booster;
import me.frxq15.boostersx.object.GPlayer;
import me.frxq15.boostersx.object.PlayerBoost;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class testCommand extends SubCommand {
    private final BoostersX plugin;

    public testCommand(BoostersX plugin) {
        super("test", "boostersx.command.admin.test", "/abooster test", null);
        this.plugin = plugin;
    }

    @Override
    public @NotNull void onCommand(@NotNull CommandSender s, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
            Player player = (Player) s;
            GPlayer gPlayer = plugin.getDataFactory().getFactory().getGPlayerData(player.getUniqueId());


            if(args.length == 0) {
                gPlayer.getBoosters().forEach(boost -> {
                    Bukkit.broadcastMessage("");
                    Bukkit.broadcastMessage("PLAYER BOOST");
                    Bukkit.broadcastMessage(boost.toString());
                    Bukkit.broadcastMessage("BOOST");
                    Bukkit.broadcastMessage(boost.getBooster().toString());
                    Bukkit.broadcastMessage("");
                });
                Bukkit.broadcastMessage("ACTIVE BOOSTERS");
                gPlayer.getActiveBoosters().forEach(boost -> {
                    Bukkit.broadcastMessage("");
                    Bukkit.broadcastMessage("PLAYER BOOST");
                    Bukkit.broadcastMessage(boost.toString());
                    Bukkit.broadcastMessage("BOOST");
                    Bukkit.broadcastMessage(boost.getBooster().toString());
                    Bukkit.broadcastMessage("");
                });
                return;
            }
            String booster = args[0];
            long duration = Long.parseLong(args[1]);
            Booster boost = plugin.getBoostsHelper().getBooster(booster);
            if(boost == null) {
                player.sendMessage(plugin.colourize("&cBooster with id " + booster + " not found!"));
                return;
            }
            PlayerBoost playerBoost = gPlayer.findFirstByIdAndDuration(booster, duration);
            if(playerBoost == null) {
                player.sendMessage(plugin.colourize("&cBooster not found!"));
                return;
            }
            gPlayer.activateBooster(playerBoost);
            playerBoost.getBooster().getActivationMessage().forEach(message -> plugin.getLocaleManager().sendRawMessage(player, message));
            return;




    }

    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        return List.of();
    }
}
