package com.devt.metrics.domain.models.metrics;

import com.devt.metrics.domain.services.helper.Formatter;

import java.time.Duration;

public record PipelineMetric(String name,
                             int totalRuns,
                             double successRate,
                             Duration averageDuration) {

    public String averageDurationFormatted() {
        return Formatter.format(averageDuration);
    }

}
