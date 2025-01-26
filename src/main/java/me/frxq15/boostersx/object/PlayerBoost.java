package me.frxq15.boostersx.object;

import me.frxq15.boostersx.BoostersX;

import java.time.Instant;

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



