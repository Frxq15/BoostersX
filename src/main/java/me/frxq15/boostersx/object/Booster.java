package me.frxq15.boostersx.object;

import me.frxq15.boostersx.BoostersX;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import java.util.ArrayList;
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
    private final Material material;
    private final boolean glow;

    public Booster(String id, double multiplier, List<String> currencies, List<String> enchants, List<String> activationMessage, List<String> deactivatedMessage, String displayName, List<String> lore, List<String> tooltip, Material material, boolean glow) {
        this.id = id;
        this.multiplier = multiplier;
        this.currencies = currencies;
        this.enchants = enchants;
        this.activationMessage = activationMessage;
        this.deactivatedMessage = deactivatedMessage;
        this.displayName = displayName;
        this.lore = lore;
        this.tooltip = tooltip;
        this.material = material;
        this.glow = glow;
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
    public Material getMaterial() {
        return material;
    }
    public boolean hasGlow() {
        return glow;
    }

    public ItemStack getBoosterItem(long duration) {
        ItemStack item = new ItemStack(material, 1);
        ItemMeta meta = item.getItemMeta();
        if (meta == null) return item;

        meta.setDisplayName(BoostersX.getInstance().colourize(displayName));

        List<String> formattedLore = new ArrayList<>();
        for (String line : lore) {
            line = line.replace("%multiplier%", String.valueOf(multiplier));
            formattedLore.add(BoostersX.getInstance().colourize(line));
        }
        meta.setLore(formattedLore);

        if (glow) {
            meta.addEnchant(Enchantment.DURABILITY, 1, true);
            meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        }

        NamespacedKey keyID = new NamespacedKey(BoostersX.getInstance(), "booster_id");
        NamespacedKey keyMultiplier = new NamespacedKey(BoostersX.getInstance(), "booster_multiplier");
        NamespacedKey keyDuration = new NamespacedKey(BoostersX.getInstance(), "booster_duration");

        PersistentDataContainer data = meta.getPersistentDataContainer();
        data.set(keyID, PersistentDataType.STRING, id);
        data.set(keyMultiplier, PersistentDataType.DOUBLE, multiplier);
        data.set(keyDuration, PersistentDataType.LONG, duration);

        item.setItemMeta(meta);
        return item;
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
                ", material=" + material +
                ", glow=" + glow +
                '}';
    }
}
