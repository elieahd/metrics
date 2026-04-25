package com.devt.metrics.domain.models.metrics;

import com.devt.metrics.domain.models.levels.DeploymentFrequencyLevel;

import java.util.List;

public record DeploymentFrequencyMetric(double average,
                                        List<DeploymentFrequencyEntry> frequencies,
                                        DeploymentFrequencyLevel level) {

    public static DeploymentFrequencyMetric empty() {
        return new DeploymentFrequencyMetric(0.0, List.of(), DeploymentFrequencyLevel.LOW);
    }

    public boolean isElite() {
        return DeploymentFrequencyLevel.ELITE.equals(level);
    }

    public boolean isHigh() {
        return DeploymentFrequencyLevel.HIGH.equals(level);
    }

    public boolean isMedium() {
        return DeploymentFrequencyLevel.MEDIUM.equals(level);
    }

    public boolean isLow() {
        return DeploymentFrequencyLevel.LOW.equals(level);
    }

    public int maxDeploymentsPerPeriod() {
        if (frequencies.isEmpty()) {
            return 1;
        }
        return frequencies.stream()
                .mapToInt(DeploymentFrequencyEntry::count)
                .max()
                .orElse(1);
    }
}
