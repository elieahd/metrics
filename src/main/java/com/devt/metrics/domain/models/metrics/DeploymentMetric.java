package com.devt.metrics.domain.models.metrics;

import java.util.List;

public record DeploymentMetric(int totalPlanned,
                               int totalHotfixes,
                               int totalDeployments,
                               double hotfixRatio,
                               List<DeploymentFrequency> frequencies,
                               double deploymentsPerPeriod) {

    public int maxDeploymentsPerPeriod() {
        if (frequencies.isEmpty()) {
            return 1;
        }
        return frequencies.stream()
                .mapToInt(DeploymentFrequency::count)
                .max()
                .orElse(1);
    }
}
