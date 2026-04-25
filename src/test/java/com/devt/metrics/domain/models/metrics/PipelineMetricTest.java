package com.devt.metrics.domain.models.metrics;

import org.junit.jupiter.api.Test;

import static com.devt.randomizer.RandomizerUtils.random;
import static org.assertj.core.api.Assertions.assertThat;

class PipelineMetricTest {

    @Test
    void withNoRuns_shouldInitiateWithName() {
        // Arrange
        String name = random(String.class);
        // Act
        PipelineMetric pipelineWithNoRuns = PipelineMetric.withNoRuns(name);
        // Assert
        assertThat(pipelineWithNoRuns.name()).isEqualTo(name);
        assertThat(pipelineWithNoRuns.totalRuns()).isZero();
        assertThat(pipelineWithNoRuns.successRate()).isZero();
        assertThat(pipelineWithNoRuns.averageDuration()).isZero();
    }

}
