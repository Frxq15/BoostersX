package me.frxq15.boostersx.command;

import me.frxq15.boostersx.BoostersX;
import me.frxq15.boostersx.command.commands.aboosterCommand;
import me.frxq15.boostersx.command.commands.boosterCommand;
import me.frxq15.boostersx.command.commands.boostersCommand;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.PluginCommand;
import org.bukkit.command.TabCompleter;
import org.jetbrains.annotations.NotNull;

import java.util.logging.Level;

public record CommandHandler(BoostersX plugin) {

    public void load() {
        registerCommands();
    }

    private void registerCommands() {
        registerCommand("abooster", new aboosterCommand(plugin));
        registerCommand("booster", new boosterCommand(plugin));
        plugin.getCommand("boosters").setExecutor(new boostersCommand());
    }

    private void registerCommand(@NotNull String name, @NotNull CommandExecutor commandExecutor) {
        PluginCommand command = plugin.getCommand(name);

        if (command == null) {
            plugin.getLogger().log(Level.WARNING, "Command " + name + " is not registered in the plugin.yml! Skipping command...");
            return;
        }

        command.setExecutor(commandExecutor);

        if (commandExecutor instanceof TabCompleter)
            command.setTabCompleter((TabCompleter) commandExecutor);
    }
}
