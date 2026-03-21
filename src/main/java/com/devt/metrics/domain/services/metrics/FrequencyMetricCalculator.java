package com.devt.metrics.domain.services.metrics;

import com.devt.metrics.domain.models.levels.DeploymentFrequencyLevel;
import com.devt.metrics.domain.models.metrics.DeploymentFrequencyEntry;
import com.devt.metrics.domain.models.metrics.DeploymentFrequencyMetric;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class FrequencyMetricCalculator implements MetricCalculator<List<OffsetDateTime>, DeploymentFrequencyMetric> {

    @Override
    public DeploymentFrequencyMetric apply(List<OffsetDateTime> deploymentDates) {

        List<DeploymentFrequencyEntry> frequencies = frequencies(deploymentDates);

        double deploymentsPerPeriod = frequencies.isEmpty()
                ? 0.0
                : (double) deploymentDates.size() / frequencies.size();

        DeploymentFrequencyLevel level = DeploymentFrequencyLevel.of(deploymentsPerPeriod);

        return new DeploymentFrequencyMetric(
                deploymentsPerPeriod,
                frequencies,
                level
        );
    }


    private List<DeploymentFrequencyEntry> frequencies(List<OffsetDateTime> deploymentsDates) {
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
                .orElse(OffsetDateTime.now());

        OffsetDateTime now = OffsetDateTime.now();

        List<DeploymentFrequencyEntry> frequencies = new ArrayList<>();
        OffsetDateTime current = start;
        while (!current.isAfter(now)) {
            String periodKey = period(current);
            int count = countsMap.getOrDefault(periodKey, 0L).intValue();
            frequencies.add(new DeploymentFrequencyEntry(periodKey, count));
            current = current.plusMonths(3);
        }
        return frequencies;
    }

    private String period(OffsetDateTime date) {
        int quarter = (date.getMonthValue() - 1) / 3 + 1;
        return "Q" + quarter + " " + date.getYear();
    }

}
