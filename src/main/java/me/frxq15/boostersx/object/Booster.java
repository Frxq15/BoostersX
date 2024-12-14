package me.frxq15.boostersx.object;

import me.frxq15.boostersx.BoostersX;

import java.util.List;

public class Booster {
    private final String id;
    private final List<String> currencies;
    private final double multiplier;

    public Booster(String id, double multiplier, List<String> currencies) {
        this.id = id;
        this.multiplier = multiplier;
        this.currencies = currencies;
    }

    public String getID() {
        return id;
    }

    public double getMultiplier() {
        return multiplier;
    }

    public List<String> getCurrencies() {
        return currencies;
    }
    @Override
    public String toString() {
        return "Booster: " + id + " Multiplier: " + multiplier + " Currencies: " + currencies;
    }
}
