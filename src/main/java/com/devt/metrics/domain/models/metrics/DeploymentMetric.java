package com.devt.metrics.domain.models.metrics;

public record DeploymentMetric(int totalChanges,
                               int totalHotfixes,
                               int totalDeployments,
                               double changeFailureRate) {
}
