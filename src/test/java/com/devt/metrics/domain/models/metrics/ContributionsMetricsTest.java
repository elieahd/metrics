package com.devt.metrics.domain.models.metrics;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ContributionsMetricsTest {

    @Test
    void empty_shouldInitializeCFRMetricWithEmptyValues() {
        // Act
        ContributionsMetrics sut = ContributionsMetrics.empty();
        // Assert
        assertThat(sut.totalContributors()).isZero();
        assertThat(sut.totalActiveContributors()).isZero();
        assertThat(sut.averageTimeTo10thPR()).isZero();
        assertThat(sut.contributors()).isEmpty();
    }
}
