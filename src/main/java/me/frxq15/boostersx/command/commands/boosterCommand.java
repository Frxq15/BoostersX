package me.frxq15.boostersx.command.commands;

import me.frxq15.boostersx.BoostersX;
import me.frxq15.boostersx.command.ParentCommand;
import me.frxq15.boostersx.command.SubCommand;
import me.frxq15.boostersx.command.subcommands.abooster.giveCommand;
import me.frxq15.boostersx.command.subcommands.abooster.testCommand;
import me.frxq15.boostersx.command.subcommands.booster.activeCommand;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;

public class boosterCommand extends ParentCommand {

    public boosterCommand(BoostersX plugin) {
        super(plugin, "booster", "boostersx.command.booster");
        register(new activeCommand(plugin));
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!sender.hasPermission(permission)) {
            plugin.getLocaleManager().sendMessage(sender, "NO_PERMISSION");
            return true;
        }
        if (args.length == 0) {
            return true;
        }
        subLabel = args[0];
        subArgs = Arrays.copyOfRange(args, 1, args.length);

        if (!exists(subLabel)) {
            plugin.getLocaleManager().sendMessage(sender, "SUBCOMMAND_NOT_FOUND");
            return true;
        }
        SubCommand subCommand = getExecutor(subLabel);

        if (!sender.hasPermission(subCommand.getPermission())) {
            plugin.getLocaleManager().sendMessage(sender, "NO_PERMISSION");
            return true;
        }
        subCommand.onCommand(sender, command, subLabel, subArgs);
        return true;
    }
}
