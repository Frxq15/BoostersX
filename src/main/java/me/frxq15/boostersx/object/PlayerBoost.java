package me.frxq15.boostersx.object;

import java.time.Instant;

public class PlayerBoost {
    private final Booster booster;
    private final long duration;
    private Instant startTime;
    private boolean isActive;

    public PlayerBoost(Booster booster, long duration) {
        this.booster = booster;
        this.duration = duration;
        this.isActive = false;
    }

    public Booster getBooster() {
        return booster;
    }

    public long getDuration() {
        return duration;
    }

    public void startTimer() {
        this.startTime = Instant.now();
        this.isActive = true;
    }

    public void activate(Instant startTime) {
        this.startTime = startTime != null ? startTime : Instant.now();
        this.isActive = true;
    }

    public boolean isActive() {
        return isActive;
    }

    public Instant getStartTime() {
        return startTime;
    }

    public void setStartTime(Instant startTime) {
        this.startTime = startTime;
    }

    public boolean isExpired() {
        if (!isActive || startTime == null) {
            return false; // Inactive or not started
        }
        return Instant.now().isAfter(startTime.plusSeconds(duration));
    }

    @Override
    public String toString() {
        return "PlayerBoost{" +
                "booster=" + booster +
                ", duration=" + duration +
                ", startTime=" + startTime +
                ", active=" + isActive +
                ", expired=" + isExpired() +
                '}';
    }
}

