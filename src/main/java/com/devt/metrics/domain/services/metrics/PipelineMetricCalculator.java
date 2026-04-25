package com.devt.metrics.domain.services.metrics;

import com.devt.metrics.domain.models.entities.Pipeline;
import com.devt.metrics.domain.models.entities.PipelineRun;
import com.devt.metrics.domain.models.metrics.PipelineMetric;

import java.time.Duration;

public class PipelineMetricCalculator implements MetricCalculator<Pipeline, PipelineMetric> {

    @Override
    public PipelineMetric apply(Pipeline pipeline) {

        if (pipeline.runs() == null || pipeline.runs().isEmpty()) {
            return PipelineMetric.withNoRuns(pipeline.name());
        }

        long totalRuns = pipeline.runs().size();
        long successCount = 0;
        Duration totalDuration = Duration.ZERO;

        for (PipelineRun run : pipeline.runs()) {
            if (run.success()) {
                successCount++;
            }
            totalDuration = totalDuration.plus(run.duration());
        }

        var successRate = (double) successCount / totalRuns;
        var averageDuration = totalDuration.dividedBy(totalRuns);

        return new PipelineMetric(
                pipeline.name(),
                totalRuns,
                successRate,
                averageDuration
        );
    }

}
