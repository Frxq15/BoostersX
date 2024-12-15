package me.frxq15.boostersx.object;

import java.time.Instant;

public class GlobalBoost {
    private final Booster booster;
    private final long duration;
    private final Instant startTime;

    public GlobalBoost(Booster booster, long duration) {
        this.booster = booster;
        this.duration = duration;
        this.startTime = Instant.now();
    }

    public Booster getBooster() {
        return booster;
    }

    public long getDuration() {
        return duration;
    }

    public Instant getStartTime() {
        return startTime;
    }

    public boolean isExpired() {
        return Instant.now().isAfter(startTime.plusSeconds(duration));
    }

    @Override
    public String toString() {
        return "GlobalBoost{" +
                "booster=" + booster +
                ", duration=" + duration +
                ", startTime=" + startTime +
                ", expired=" + isExpired() +
                '}';
    }
}

