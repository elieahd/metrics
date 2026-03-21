package com.devt.metrics.domain.models.levels;

public enum CFRLevel {

    ELITE,
    HIGH,
    MEDIUM,
    LOW;

    public static CFRLevel of(final double cfr) {
        if (cfr <= 5) {
            return CFRLevel.ELITE;
        }
        if (cfr <= 15) {
            return CFRLevel.HIGH;
        }
        if (cfr <= 30) {
            return CFRLevel.MEDIUM;
        }
        return CFRLevel.LOW;
    }
}
