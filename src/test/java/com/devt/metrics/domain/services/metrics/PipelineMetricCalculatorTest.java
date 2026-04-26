package com.devt.metrics.domain.services.metrics;

import com.devt.metrics.domain.models.entities.Pipeline;
import com.devt.metrics.domain.models.entities.PipelineRun;
import com.devt.metrics.domain.models.metrics.PipelineMetric;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.OffsetDateTime;
import java.util.List;

import static com.devt.metrics.domain.models.ModelRandomizer.aFailedPipelineRun;
import static com.devt.metrics.domain.models.ModelRandomizer.aPipeline;
import static com.devt.metrics.domain.models.ModelRandomizer.aSuccessfullPipelineRun;
import static org.assertj.core.api.Assertions.assertThat;

class PipelineMetricCalculatorTest {

    private MetricCalculator<Pipeline, PipelineMetric> sut;

    @BeforeEach
    void setUp() {
        sut = new PipelineMetricCalculator();
    }

    @Test
    void apply_shouldReturnEmptyMetrics_whenRunsIsNull() {
        // Arrange
        Pipeline pipeline = aPipeline(null);
        // Act
        PipelineMetric metric = sut.apply(pipeline);
        // Assert
        assertThat(metric.name()).isEqualTo(pipeline.name());
        assertThat(metric.totalRuns()).isZero();
        assertThat(metric.successRate()).isZero();
        assertThat(metric.averageDuration()).isZero();
    }

    @Test
    void apply_shouldReturnEmptyMetrics_whenRunsIsEmpty() {
        // Arrange
        Pipeline pipeline = aPipeline(List.of());
        // Act
        PipelineMetric metric = sut.apply(pipeline);
        // Assert
        assertThat(metric.name()).isEqualTo(pipeline.name());
        assertThat(metric.totalRuns()).isZero();
        assertThat(metric.successRate()).isZero();
        assertThat(metric.averageDuration()).isZero();
    }

    @Test
    void apply_shouldReturnMetrics_whenSingleSuccessfulRun() {
        // Arrange
        OffsetDateTime start = OffsetDateTime.now();
        Pipeline pipeline = aPipeline(List.of(
                aSuccessfullPipelineRun(start, start.plusMinutes(5))
        ));
        // Act
        PipelineMetric metric = sut.apply(pipeline);
        // Assert
        assertThat(metric.name()).isEqualTo(pipeline.name());
        assertThat(metric.totalRuns()).isEqualTo(1);
        assertThat(metric.successRate()).isEqualTo(1.0);
        assertThat(metric.averageDuration()).isEqualTo(Duration.ofMinutes(5));
    }

    @Test
    void apply_shouldReturnMetrics_whenSingleFailedRun() {
        // Arrange
        OffsetDateTime start = OffsetDateTime.now();
        Pipeline pipeline = aPipeline(List.of(
                aFailedPipelineRun(start, start.plusMinutes(3))
        ));
        // Act
        PipelineMetric metric = sut.apply(pipeline);
        // Assert
        assertThat(metric.name()).isEqualTo(pipeline.name());
        assertThat(metric.totalRuns()).isEqualTo(1);
        assertThat(metric.successRate()).isZero();
        assertThat(metric.averageDuration()).isEqualTo(Duration.ofMinutes(3));
    }

    @Test
    void apply_shouldReturnFullSuccessRate_whenAllRunsSucceeded() {
        // Arrange
        OffsetDateTime start = OffsetDateTime.now();
        List<PipelineRun> runs = List.of(
                aSuccessfullPipelineRun(start, start.plusMinutes(2)),
                aSuccessfullPipelineRun(start, start.plusMinutes(4)),
                aSuccessfullPipelineRun(start, start.plusMinutes(6))
        );
        Pipeline pipeline = aPipeline(runs);
        // Act
        PipelineMetric metric = sut.apply(pipeline);
        // Assert
        assertThat(metric.name()).isEqualTo(pipeline.name());
        assertThat(metric.totalRuns()).isEqualTo(3);
        assertThat(metric.successRate()).isEqualTo(1.0);
        assertThat(metric.averageDuration()).isEqualTo(Duration.ofMinutes(4));
    }

    @Test
    void apply_shouldReturnZeroSuccessRate_whenAllRunsFailed() {
        // Arrange
        OffsetDateTime start = OffsetDateTime.now();
        List<PipelineRun> runs = List.of(
                aFailedPipelineRun(start, start.plusMinutes(1)),
                aFailedPipelineRun(start, start.plusMinutes(3)),
                aFailedPipelineRun(start, start.plusMinutes(5))
        );
        Pipeline pipeline = aPipeline(runs);
        // Act
        PipelineMetric metric = sut.apply(pipeline);
        // Assert
        assertThat(metric.name()).isEqualTo(pipeline.name());
        assertThat(metric.totalRuns()).isEqualTo(3);
        assertThat(metric.successRate()).isZero();
        assertThat(metric.averageDuration()).isEqualTo(Duration.ofMinutes(3));
    }

    @Test
    void apply_shouldReturnPartialSuccessRate_whenRunsAreMixed() {
        // Arrange
        OffsetDateTime start = OffsetDateTime.now();
        List<PipelineRun> runs = List.of(
                aSuccessfullPipelineRun(start, start.plusMinutes(2)),
                aFailedPipelineRun(start, start.plusMinutes(4)),
                aSuccessfullPipelineRun(start, start.plusMinutes(6)),
                aFailedPipelineRun(start, start.plusMinutes(8))
        );
        Pipeline pipeline = aPipeline(runs);
        // Act
        PipelineMetric metric = sut.apply(pipeline);
        // Assert
        assertThat(metric.name()).isEqualTo(pipeline.name());
        assertThat(metric.totalRuns()).isEqualTo(4);
        assertThat(metric.successRate()).isEqualTo(0.5);
        assertThat(metric.averageDuration()).isEqualTo(Duration.ofMinutes(5));
    }

    @Test
    void apply_shouldReturnZeroDuration_whenStartedAtOrUpdatedAtIsNull() {
        // Arrange
        List<PipelineRun> runs = List.of(
                aSuccessfullPipelineRun(null, OffsetDateTime.now()),
                aFailedPipelineRun(OffsetDateTime.now(), null)
        );
        Pipeline pipeline = aPipeline(runs);
        // Act
        PipelineMetric metric = sut.apply(pipeline);
        // Assert
        assertThat(metric.name()).isEqualTo(pipeline.name());
        assertThat(metric.totalRuns()).isEqualTo(2);
        assertThat(metric.successRate()).isEqualTo(0.5);
        assertThat(metric.averageDuration()).isZero();
    }
}
