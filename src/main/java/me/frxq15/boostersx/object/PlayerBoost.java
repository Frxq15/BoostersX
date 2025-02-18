package me.frxq15.boostersx.object;

import me.frxq15.boostersx.BoostersX;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

public class PlayerBoost {
    private final Booster booster;
    private final long duration;
    private long startTime;
    private boolean isActive;

    public PlayerBoost(Booster booster, long duration) {
        this.booster = booster;
        this.duration = duration;
        this.isActive = false;
        this.startTime = -1;
    }

    public Booster getBooster() {
        return booster;
    }

    public ItemStack getBoosterItem() {
        ItemStack item = new ItemStack(booster.getMaterial(), 1);
        ItemMeta meta = item.getItemMeta();
        if (meta == null) return item;

        meta.setDisplayName(BoostersX.getInstance().colourize(booster.getDisplayName()));

        List<String> formattedLore = new ArrayList<>();
        for (String line : booster.getLore()) {
            line = line.replace("%multiplier%", String.valueOf(booster.getMultiplier()));
            line = line.replace("%duration%", getDurationFormatted());
            formattedLore.add(BoostersX.getInstance().colourize(line));
        }
        meta.setLore(formattedLore);

        if (booster.hasGlow()) {
            meta.addEnchant(Enchantment.DURABILITY, 1, true);
            meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        }

        item.setItemMeta(meta);
        return item;
    }

    public long getDuration() {
        return duration;
    }

    public void startTimer() {
        this.startTime = System.currentTimeMillis();
        this.isActive = true;
    }

    public void activate(Long startTime) {
        this.startTime = (startTime != null) ? startTime : System.currentTimeMillis();
        this.isActive = true;
    }

    public boolean isActive() {
        return isActive;
    }

    public long getStartTime() {
        return startTime;
    }

    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }

    public boolean isExpired() {
        if (!isActive || startTime == -1) {
            return false;
        }
        boolean expired = System.currentTimeMillis() > (startTime + duration);
        if (expired) {
            isActive = false;
        }
        return expired;
    }
    public String getTimeRemaining() {
        if (!isActive || startTime == -1) {
            return "Not Active";
        }
        long timeRemaining = startTime + duration - System.currentTimeMillis();
        return BoostersX.getInstance().getTimeUtils().formatTimeRemaining(timeRemaining);
    }
    public String getDurationFormatted() {
        return BoostersX.getInstance().getTimeUtils().formatDuration(duration);
    }

    @Override
    public String toString() {
        return "PlayerBoost{" +
                "booster=" + booster +
                ", duration=" + duration +
                ", startTime=" + (startTime == -1 ? "Not Started" : startTime) +
                ", active=" + isActive +
                ", expired=" + isExpired() +
                '}';
    }
}



