package me.frxq15.boostersx.command.subcommands.booster;

import me.frxq15.boostersx.BoostersX;
import me.frxq15.boostersx.command.SubCommand;
import me.frxq15.boostersx.object.GPlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import java.util.List;

public class activeCommand extends SubCommand {
    private final BoostersX plugin;

    public activeCommand(BoostersX plugin) {
        super("active", "boostersx.command.active", "/booster active", null);
        this.plugin = plugin;
    }

    @Override
    public @NotNull void onCommand(@NotNull CommandSender s, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if(args.length == 0) {
            if(!(s instanceof Player)) {
                plugin.log("This command cannot be executed from console.");
            }
            Player player = (Player) s;
            GPlayer gPlayer = plugin.getDataFactory().getFactory().getGPlayerData(player.getUniqueId());

            if(gPlayer.getActiveBoosters().isEmpty()) {
                plugin.getLocaleManager().sendMessage(s, "NO_ACTIVE_BOOSTERS");
                return;
            }
            plugin.getConfig().getStringList("MESSAGES.BOOSTERS.ACTIVE_HEADER").forEach(line -> {
                plugin.getLocaleManager().sendRawMessage(player, line);
            });
            gPlayer.getActiveBoosters().forEach(boost -> {
                 String message = plugin.getConfig().getString("MESSAGES.BOOSTERS.ACTIVE_FORMAT").replace("%booster%", boost.getBooster().getDisplayName());
                 plugin.getPlayerUtils().sendPlayerBoosterTooltip(player, plugin.colourize(message), boost, boost.getBooster().getTooltip());
            });
            plugin.getConfig().getStringList("MESSAGES.BOOSTERS.ACTIVE_FOOTER").forEach(line -> {
                plugin.getLocaleManager().sendRawMessage(player, line);
            });
            return;
        }
    }

    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        return List.of();
    }
}
