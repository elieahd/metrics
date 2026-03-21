package com.devt.metrics.domain.models.levels;

public enum DeploymentFrequencyLevel {

    ELITE,
    HIGH,
    MEDIUM,
    LOW;

    public static DeploymentFrequencyLevel of(double average) {
        if (average > 90) {
            return DeploymentFrequencyLevel.ELITE;
        }
        if (average > 13) {
            return DeploymentFrequencyLevel.HIGH;
        }
        if (average > 3) {
            return DeploymentFrequencyLevel.MEDIUM;
        }
        return DeploymentFrequencyLevel.LOW;
    }
}
