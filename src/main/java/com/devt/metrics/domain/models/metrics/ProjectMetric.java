package com.devt.metrics.domain.models.metrics;

import java.util.List;

public record ProjectMetric(String name,
                            List<RepositoryMetric> repositories,
                            ContributionsMetrics contributions,
                            DeploymentMetric deployments) {
}
