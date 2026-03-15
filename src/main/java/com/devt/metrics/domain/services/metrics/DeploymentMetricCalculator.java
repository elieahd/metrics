package com.devt.metrics.domain.services.metrics;

import com.devt.metrics.domain.models.metrics.DeploymentMetric;
import com.devt.metrics.domain.models.metrics.ReleaseMetric;
import com.devt.metrics.domain.models.metrics.RepositoryMetric;

import java.util.List;

public class DeploymentMetricCalculator implements MetricCalculator<List<RepositoryMetric>, DeploymentMetric> {

    @Override
    public DeploymentMetric apply(List<RepositoryMetric> repositories) {

        var changes = 0;
        var hotfixes = 0;

        for (RepositoryMetric repository : repositories) {
            for (ReleaseMetric release : repository.releases()) {
                if (release.isChange()) {
                    changes++;
                } else {
                    hotfixes++;
                }
            }
        }

        var deployments = hotfixes + changes;

        var changeFailureRate = deployments > 0
                ? (double) hotfixes / deployments
                : 0.0;

        return new DeploymentMetric(
                changes,
                hotfixes,
                deployments,
                changeFailureRate
        );
    }

}
