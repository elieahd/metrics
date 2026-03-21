package com.devt.metrics.domain.services.metrics;

import com.devt.metrics.domain.models.metrics.CFRMetric;
import com.devt.metrics.domain.models.metrics.DeploymentFrequencyMetric;
import com.devt.metrics.domain.models.metrics.DeploymentMetric;
import com.devt.metrics.domain.models.metrics.ReleaseMetric;
import com.devt.metrics.domain.models.metrics.RepositoryMetric;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

public class DeploymentMetricCalculator implements MetricCalculator<List<RepositoryMetric>, DeploymentMetric> {

    private final MetricCalculator<Double, CFRMetric> cfrCalculator;
    private final MetricCalculator<List<OffsetDateTime>, DeploymentFrequencyMetric> frequencyCalculator;

    public DeploymentMetricCalculator() {
        this.cfrCalculator = new CFRMetricCalculator();
        this.frequencyCalculator = new FrequencyMetricCalculator();
    }

    @Override
    public DeploymentMetric apply(List<RepositoryMetric> repositories) {

        var planned = 0;
        var hotfixes = 0;
        var deployments = 0;

        List<OffsetDateTime> deploymentsDates = new ArrayList<>();

        for (RepositoryMetric repository : repositories) {
            for (ReleaseMetric release : repository.releases()) {
                if (release.isPlanned()) {
                    planned++;
                } else {
                    hotfixes++;
                }
                deployments++;
                deploymentsDates.add(release.publishedAt());
            }
        }

        double hotfixRatio = deployments > 0
                ? (double) hotfixes / deployments
                : 0.0;

        CFRMetric cfrMetric = cfrCalculator.apply(hotfixRatio);

        DeploymentFrequencyMetric frequencyMetric = frequencyCalculator.apply(deploymentsDates);

        return new DeploymentMetric(
                planned,
                hotfixes,
                deployments,
                hotfixRatio,
                cfrMetric,
                frequencyMetric
        );
    }


}
