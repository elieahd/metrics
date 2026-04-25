package com.devt.metrics.domain.models.metrics;

public record DeploymentMetric(long totalPlanned,
                               long totalHotfixes,
                               long totalDeployments,
                               double hotfixRatio,
                               CFRMetric cfrMetric,
                               DeploymentFrequencyMetric frequencyMetric) {

    public static DeploymentMetric empty() {
        return new DeploymentMetric(0, 0, 0, 0.0, CFRMetric.empty(), DeploymentFrequencyMetric.empty());
    }

}
