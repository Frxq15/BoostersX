package me.frxq15.boostersx.command.commands;

import me.frxq15.boostersx.BoostersX;
import me.frxq15.boostersx.menu.menus.PersonalBoostersMenu;
import me.frxq15.boostersx.object.GPlayer;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class boostersCommand implements CommandExecutor {
    private BoostersX plugin = BoostersX.getInstance();

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if(!(sender instanceof Player player)) {
            plugin.log("This command cannot be executed from console.");
            return true;
        }
        if(!sender.hasPermission("boostersx.command.boosters")) {
            plugin.getLocaleManager().sendMessage(sender, "NO_PERMISSION");
            return true;
        }
        if(args.length == 0) {
            new PersonalBoostersMenu(plugin, player, player.getUniqueId()).open(player);
            return true;
        }
        if(args.length == 1) {
            if(!sender.hasPermission("boostersx.command.boosters.other")) {
                plugin.getLocaleManager().sendMessage(sender, "NO_PERMISSION");
                return true;
            }
            OfflinePlayer target = Bukkit.getOfflinePlayer(args[0]);
            if (!GPlayer.players.containsKey(target.getUniqueId())) {
                plugin.getLocaleManager().sendMessage(player, "PLAYER_NOT_FOUND");
                return true;
            }
            new PersonalBoostersMenu(plugin, player, target.getUniqueId()).open(player);
            return true;
        }
        return true;
    }
}
