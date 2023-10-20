package net.kamilereon.lylac_system.utils;

public class RangeDouble {
    private double min;
    private double max;

    public RangeDouble(double min, double max) {
        this.min = min;
        this.max = max;
    }

    public double getRandomNumber() {
        double rnd = Math.random();
        double diff = max - min;
        rnd *= diff;
        return rnd + min;
    }
}
