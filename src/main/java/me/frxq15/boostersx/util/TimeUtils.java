package me.frxq15.boostersx.util;

import me.frxq15.boostersx.BoostersX;

import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

        long totalHours = totalMinutes / 60;
        long hours = totalHours % 24;

        long days = totalHours / 24;

        StringBuilder result = new StringBuilder();

        if (days > 0) {
            result.append(days).append(" day").append(days == 1 ? "" : "s");
        }
        if (hours > 0) {
            if (result.length() > 0) result.append(" ");
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
    public long parseTime(String input) {
        long totalMillis = 0;
        Pattern pattern = Pattern.compile("(\\d+)(d|h|min|s)");
        Matcher matcher = pattern.matcher(input.toLowerCase());

        while (matcher.find()) {
            int value = Integer.parseInt(matcher.group(1));
            String unit = matcher.group(2);

            switch (unit) {
                case "d" -> totalMillis += TimeUnit.DAYS.toMillis(value);
                case "h" -> totalMillis += TimeUnit.HOURS.toMillis(value);
                case "min" -> totalMillis += TimeUnit.MINUTES.toMillis(value);
                case "s" -> totalMillis += TimeUnit.SECONDS.toMillis(value);
            }
        }

        return totalMillis;
    }
}
