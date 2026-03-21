package com.devt.metrics.domain.models.metrics;

import com.devt.metrics.domain.models.levels.CFRLevel;

public record CFRMetric(double value,
                        CFRLevel level) {

    public double percentage() {
        return value * 100.0;
    }

    public boolean isElite() {
        return CFRLevel.ELITE.equals(level);
    }

    public boolean isHigh() {
        return CFRLevel.HIGH.equals(level);
    }

    public boolean isMedium() {
        return CFRLevel.MEDIUM.equals(level);
    }

    public boolean isLow() {
        return CFRLevel.LOW.equals(level);
    }
}
