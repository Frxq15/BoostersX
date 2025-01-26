package me.frxq15.boostersx.util;

import me.frxq15.boostersx.BoostersX;
import me.frxq15.boostersx.object.PlayerBoost;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.entity.Player;

import java.util.List;

public class PlayerUtils {
    private final BoostersX plugin;

    public PlayerUtils(BoostersX plugin) {
        this.plugin = plugin;
    }
    public void sendPlayerBoosterTooltip(Player player, String message, PlayerBoost boost, List<String> tooltip) {
        tooltip = tooltip.stream().map(plugin::colourize).toList();
        tooltip = tooltip.stream()
                .map(s -> s.replace("%timeleft%", boost.getTimeRemaining())
                        .replace("%duration%", boost.getDurationFormatted()))
                .toList();

        String tooltipText = String.join("\n", tooltip);

        String formattedMessage = plugin.colourize(message.replace("%booster%", boost.getBooster().getDisplayName()));

        TextComponent baseMessage = new TextComponent(formattedMessage);

        baseMessage.setHoverEvent(new HoverEvent(
                HoverEvent.Action.SHOW_TEXT,
                new ComponentBuilder(tooltipText).create()
        ));

        player.spigot().sendMessage(baseMessage);
    }
}
