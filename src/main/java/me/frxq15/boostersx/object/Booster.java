package me.frxq15.boostersx.object;

import me.frxq15.boostersx.BoostersX;

import java.util.List;

public class Booster {
    private final String id;
    private final List<String> currencies;
    private final double multiplier;
    private final List<String> enchants;
    private final List<String> activationMessage;
    private final List<String> deactivatedMessage;
    private final String displayName;
    private final List<String> lore;
    private final List<String> tooltip;

    public Booster(String id, double multiplier, List<String> currencies, List<String> enchants, List<String> activationMessage, List<String> deactivatedMessage, String displayName, List<String> lore, List<String> tooltip) {
        this.id = id;
        this.multiplier = multiplier;
        this.currencies = currencies;
        this.enchants = enchants;
        this.activationMessage = activationMessage;
        this.deactivatedMessage = deactivatedMessage;
        this.displayName = displayName;
        this.lore = lore;
        this.tooltip = tooltip;
    }

    public String getID() {
        return id;
    }
    public double getMultiplier() {
        return multiplier;
    }
    public List<String> getEnchants() {
        return enchants;
    }

    public List<String> getCurrencies() {
        return currencies;
    }
    public List<String> getActivationMessage() {
        return activationMessage;
    }
    public List<String> getDeactivatedMessage() {
        return deactivatedMessage;
    }
    public String getDisplayName() {
        return displayName;
    }
    public List<String> getLore() {
        return lore;
    }
    public List<String> getTooltip() {
        return tooltip;
    }
    @Override
    public String toString() {
        return "Booster{" +
                "id='" + id + '\'' +
                ", multiplier=" + multiplier +
                ", currencies=" + currencies +
                ", enchants=" + enchants +
                ", activationMessage=" + activationMessage +
                ", deactivatedMessage=" + deactivatedMessage +
                ", displayName='" + displayName + '\'' +
                ", lore=" + lore +
                '}';
    }
}
