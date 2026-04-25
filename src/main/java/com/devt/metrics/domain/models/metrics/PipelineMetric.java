package com.devt.metrics.domain.models.metrics;

import com.devt.metrics.domain.services.helper.Formatter;

import java.time.Duration;

public record PipelineMetric(String name,
                             long totalRuns,
                             double successRate,
                             Duration averageDuration) {

    public static PipelineMetric withNoRuns(String name) {
        return new PipelineMetric(name, 0, 0, Duration.ZERO);
    }

    public String averageDurationFormatted() {
        return Formatter.format(averageDuration);
    }

}
