package me.frxq15.boostersx.util;

import me.frxq15.boostersx.BoostersX;

public class TimeUtils {
    private BoostersX plugin;

    public TimeUtils(BoostersX plugin) {
        this.plugin = plugin;
    }

    public String formatTimeRemaining(long time) {
        long totalSeconds = time / 1000;
        long seconds = totalSeconds % 60;
        long totalMinutes = totalSeconds / 60;
        long minutes = totalMinutes % 60;
        long hours = totalMinutes / 60;
        StringBuilder result = new StringBuilder();

        if (hours > 0) {
            result.append(hours).append(" hour").append(hours == 1 ? "" : "s");
        }
        if (minutes > 0) {
            if (result.length() > 0) result.append(" ");
            result.append(minutes).append(" minute").append(minutes == 1 ? "" : "s");
        }
        if (seconds > 0 || result.length() == 0) {
            if (result.length() > 0) result.append(" ");
            result.append(seconds).append(" second").append(seconds == 1 ? "" : "s");
        }

        return result.toString();
    }
    public String formatDuration(long durationMillis) {
        long totalSeconds = durationMillis / 1000;
        long seconds = totalSeconds % 60;

        long totalMinutes = totalSeconds / 60;
        long minutes = totalMinutes % 60;

        long hours = totalMinutes / 60;

        StringBuilder result = new StringBuilder();

        if (hours > 0) {
            result.append(hours).append(" hour").append(hours == 1 ? "" : "s");
        }
        if (minutes > 0) {
            if (result.length() > 0) result.append(" ");
            result.append(minutes).append(" minute").append(minutes == 1 ? "" : "s");
        }
        if (seconds > 0 || result.length() == 0) {
            if (result.length() > 0) result.append(" ");
            result.append(seconds).append(" second").append(seconds == 1 ? "" : "s");
        }

        return result.toString();
    }
}
