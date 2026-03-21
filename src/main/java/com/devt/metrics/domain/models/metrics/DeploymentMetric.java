package com.devt.metrics.domain.models.metrics;

public record DeploymentMetric(int totalPlanned,
                               int totalHotfixes,
                               int totalDeployments,
                               double hotfixRatio,
                               CFRMetric cfrMetric,
                               DeploymentFrequencyMetric frequencyMetric) {

}
