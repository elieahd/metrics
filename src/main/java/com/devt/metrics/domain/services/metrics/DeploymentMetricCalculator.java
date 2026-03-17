package com.devt.metrics.domain.services.metrics;

import com.devt.metrics.domain.models.metrics.DeploymentFrequency;
import com.devt.metrics.domain.models.metrics.DeploymentMetric;
import com.devt.metrics.domain.models.metrics.ReleaseMetric;
import com.devt.metrics.domain.models.metrics.RepositoryMetric;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class DeploymentMetricCalculator implements MetricCalculator<List<RepositoryMetric>, DeploymentMetric> {

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

        var hotfixRatio = deployments > 0
                ? (double) hotfixes / deployments
                : 0.0;

        List<DeploymentFrequency> frequencies = frequencies(deploymentsDates);

        double deploymentsPerQuarter = frequencies.isEmpty()
                ? 0.0
                : (double) deployments / frequencies.size();

        return new DeploymentMetric(
                planned,
                hotfixes,
                deployments,
                hotfixRatio,
                frequencies,
                deploymentsPerQuarter
        );
    }

    private List<DeploymentFrequency> frequencies(List<OffsetDateTime> deploymentsDates) {
        if (deploymentsDates.isEmpty()) {
            return new ArrayList<>();
        }

        Map<String, Long> countsMap = deploymentsDates.stream()
                .collect(Collectors.groupingBy(
                        this::period,
                        Collectors.counting()
                ));

        OffsetDateTime start = deploymentsDates.stream()
                .min(OffsetDateTime::compareTo)
                .get();

        OffsetDateTime now = OffsetDateTime.now();

        List<DeploymentFrequency> frequencies = new ArrayList<>();
        OffsetDateTime current = start;
        while (!current.isAfter(now)) {
            String periodKey = period(current);
            int count = countsMap.getOrDefault(periodKey, 0L).intValue();
            frequencies.add(new DeploymentFrequency(periodKey, count));
            current = current.plusMonths(3);
        }
        return frequencies;
    }

    private String period(OffsetDateTime date) {
        int quarter = (date.getMonthValue() - 1) / 3 + 1;
        return "Q" + quarter + " " + date.getYear();
    }

}
