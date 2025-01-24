package me.frxq15.boostersx.command.commands;


import me.frxq15.boostersx.BoostersX;
import me.frxq15.boostersx.command.ParentCommand;
import me.frxq15.boostersx.command.SubCommand;
import me.frxq15.boostersx.command.subcommands.abooster.giveCommand;
import me.frxq15.boostersx.command.subcommands.abooster.testCommand;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;

public class aboosterCommand extends ParentCommand {

    public aboosterCommand(BoostersX plugin) {
        super(plugin, "abooster", "boostersx.command.admin.abooster");
        register(new giveCommand(plugin));
        register(new testCommand(plugin));
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
